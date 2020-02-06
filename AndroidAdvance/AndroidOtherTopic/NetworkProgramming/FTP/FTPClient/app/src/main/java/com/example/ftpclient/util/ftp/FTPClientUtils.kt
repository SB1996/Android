package com.example.ftpclient.util.ftp

import android.content.Context
import android.util.Log
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPFile
import org.apache.commons.net.ftp.FTPReply
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.SocketException


@Suppress("BlockingMethodInNonBlockingContext")
class FTPClientUtils {

    private val TAG: String = FTPClient::class.java.simpleName

    private lateinit var ftpClient: FTPClient

    /** Connect to FTP Server **/
    internal suspend fun connect(host: String, port: Int, username: String, password: String): Boolean {
        try {
            ftpClient = FTPClient()

            try {
                ftpClient.connect(host, port) /** connecting to the host server **/
            }catch (ex: IOException){
                ex.printStackTrace()
            }catch (ex: SocketException){
                ex.printStackTrace()
            }

            /* now check the reply code, if positive mean connection success */
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                var status: Boolean = false
                try {
                    status = ftpClient.login(username, password) /* login using username & password */
                }catch (ex: IOException){
                    ex.printStackTrace()
                }
                /*
				 * Set File Transfer Mode :
				 * 
				 * To avoid corruption issue you must specified a correct
				 * transfer mode, such as ASCII_FILE_TYPE, BINARY_FILE_TYPE,
				 * EBCDIC_FILE_TYPE .etc. Here, I use BINARY_FILE_TYPE for
				 * transferring text, image, and compressed files.
				 */
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE)
                ftpClient.enterLocalPassiveMode()

                return status
            }
        } catch (ex: Exception) {
            Log.d(TAG, "Error: could not connect to host $host")
            ex.printStackTrace()
        }

        return false
    }

    /** Disconnect to FTP Server **/
    internal suspend fun disconnect(): Boolean {
        if(ftpClient.isConnected){
            try {
                ftpClient.logout()
                ftpClient.disconnect()
                return true
            } catch (e: Exception) {
                Log.d(TAG, "Error occurred while disconnecting from ftp server.")
            }
        }else{
            Log.d(TAG, "already disconnected from FTP server.")
        }
        return false
    }

    /** Method to get current working directory **/
    internal suspend fun getCurrentWorkingDirectory(): String? {
        try {
            return ftpClient.printWorkingDirectory()
        } catch (e: Exception) {
            Log.d(TAG, "Error: could not get current working directory.")
        }
        return null
    }

    /** Method to create new directory **/
    @Throws(IOException::class)
    internal suspend fun makeDirectories(directoryPath: String): Boolean {

        val nestedDirectory: Array<String> = directoryPath.split("/").toTypedArray()

        if (nestedDirectory.isNotEmpty()){
            for (directory in nestedDirectory){
                val isExist: Boolean = ftpClient.changeWorkingDirectory(directory)
                if (!isExist) {
                    val isCreated = ftpClient.makeDirectory(directory)
                    if (isCreated) {
                        ftpClient.changeWorkingDirectory(directory)
                    } else {
                        return false
                    }
                }
            }
        }
        return true
    }

    /** Method to change working directory **/
    @Throws(IOException::class)
    internal suspend fun changeDirectory(directoryPath: String): Boolean {
        try {
            return ftpClient.changeWorkingDirectory(directoryPath)
        } catch (e: Exception) {
            Log.d(TAG, "Error: could not change directory to $directoryPath")
        }

        return false
    }

    /** Method to list all files in a directory **/
    internal suspend fun getFilesList(dirPath: String): Array<String?>? {
        var fileList: Array<String?>? = null
        try {
            val ftpFiles: Array<FTPFile> = ftpClient.listFiles(dirPath)
            val length: Int = ftpFiles.size
            fileList = arrayOfNulls<String?>(length)
            for (i in 0 until length) {
                val name = ftpFiles[i].name
                val isFile = ftpFiles[i].isFile

                if (isFile) {
                    fileList[i] = "File :: $name"
                    Log.i(TAG, "File : $name")
                } else {
                    fileList[i] = "Directory :: $name"
                    Log.i(TAG, "Directory : $name")
                }
            }
            return fileList
        } catch (e: Exception) {
            e.printStackTrace()
            return fileList
        }

    }

    /** Method to list all files in a directory And sub directory **/
    internal suspend fun getFilesMap(dirPath: String, isSubDirAllow: Boolean=false): HashMap<String, String>? {

        var fileMap: HashMap<String, String>? = null /* Map List of files */

        try {
            val ftpFiles: Array<FTPFile> = ftpClient.listFiles(dirPath)
            val length: Int = ftpFiles.size
            fileMap = HashMap()
            for (i in 0 until length) {
                val name = ftpFiles[i].name
                val isFile = ftpFiles[i].isFile
                if (isFile) {
                    Log.i(TAG, "File : $name")
                    fileMap.put(name, "$dirPath/$name")
                } else {
                    Log.i(TAG, "Directory : $name")
                    if (isSubDirAllow){
                        getFilesMap(name)
                    }
                }
            }
            return fileMap
        } catch (e: Exception) {
            e.printStackTrace()
            return fileMap
        }

    }

    /** Method to create new directory **/
    internal suspend fun makeDirectory(newDirPath: String): Boolean {
        try {
            return ftpClient.makeDirectory(newDirPath)
        } catch (e: Exception) {
            Log.d(TAG, "Error: could not create new directory named $newDirPath")
            e.printStackTrace()
        }

        return false
    }

    /** Method to delete/remove a directory **/
    internal suspend fun removeDirectory(dirPath: String): Boolean {
        try {
            return ftpClient.removeDirectory(dirPath)
        } catch (e: Exception) {
            Log.d(TAG, "Error: could not remove directory named $dirPath")
        }

        return false
    }

    /** Method to delete a file **/
    internal suspend fun removeFile(filePath: String): Boolean {
        try {
            return ftpClient.deleteFile(filePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    /** Method to rename a file **/
    internal suspend fun renameFile(from: String, to: String): Boolean {
        try {
            return ftpClient.rename(from, to)
        } catch (e: Exception) {
            Log.d(TAG, "Could not rename file: $from to: $to")
            e.printStackTrace()
        }

        return false
    }

    /** Method to download a file from FTP server **/
    /**
     * ftpClient: FTP client connection object (see FTP connection example)
     * srcFilePath: path to the source file in FTP server desFilePath: path to
     * the destination file to be saved in sdcard
     */
    internal suspend fun downloadFile(srcFilePath: String, desFilePath: String): Boolean {
        var status = false
        try {
            val desFileStream: FileOutputStream = FileOutputStream(desFilePath)
            status = ftpClient.retrieveFile(srcFilePath, desFileStream)
            desFileStream.close()

            return status
        } catch (ex: Exception) {
            Log.d(TAG, "download file failed")
            ex.printStackTrace()
        }

        return status
    }

    /** Method to upload a file to FTP server **/
    /**
     * ftpClient: FTP client connection object (see FTP connection example)
     * srcFilePath: source file path in sdcard desFileName: file name to be
     * stored in FTP server desDirectory: directory path where the file should
     * be upload to
     */
    internal suspend fun uploadFile(srcFilePath: String, desFileName: String, desDirectory: String, context: Context): Boolean {
        var status = false
        try {
            val srcFileStream = FileInputStream(srcFilePath)

            // change working directory to the destination directory
            // if (ftpChangeDirectory(desDirectory)) {
            status = ftpClient.storeFile(desFileName, srcFileStream)
            // }

            srcFileStream.close()

            return status
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "upload failed: $e")
        }

        return status
    }





}