package com.example.kodinedependenceyinjection.bike.component

import com.example.kodinedependenceyinjection.bike.subComponent.tools.Tools

class Tool(val tools: Tools) {

    private val TAG: String = Tool::class.java.simpleName

    /** Config Tools Setup **/
    fun configTool(){
        tools.doReadyAllTools()
    }


}