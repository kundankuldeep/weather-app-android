package com.app.weatherapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {

//    +-----------------------------------+
//    |         Dispatchers.Main          |
//    +-----------------------------------+
//    | Main thread on Android, interact  |
//    | with the UI and perform light     |
//    | work                              |
//    +-----------------------------------+
//    | - Calling suspend functions       |
//    | - Call UI functions               |
//    | - Updating LiveData               |
//    +-----------------------------------+

    fun main(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

//    +-----------------------------------+
//    |          Dispatchers.IO           |
//    +-----------------------------------+
//    | Optimized for disk and network IO |
//    | off the main thread               |
//    +-----------------------------------+
//    | - Database*                       |
//    | - Reading/writing files           |
//    | - Networking**                    |
//    +-----------------------------------+

    fun io(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

//    +-----------------------------------+
//    |        Dispatchers.Default        |
//    +-----------------------------------+
//    | Optimized for CPU intensive work  |
//    | off the main thread               |
//    +-----------------------------------+
//    | - Sorting a list                  |
//    | - Parsing JSON                    |
//    | - DiffUtils                       |
//    +-----------------------------------+

    fun default(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Default).launch {
            work()
        }

}