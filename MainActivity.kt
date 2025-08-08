package com.example.flows

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    val channel = Channel<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//
//        GlobalScope.launch(Dispatchers.Main){
//            val k = producers()
////            delay(2500)
//                k.collect{
//                Log.d("TAG", "onCreate 1: $it")
//            }
//        }

        GlobalScope.launch(Dispatchers.Main) {
            val k = producers()
         //   delay(10000)
//            k.collect{
//                Log.d("TAG", "onCreate 2: $it")
//            }
           Log.d("TAG", "onCreate 3: ${k.value}")
        }

//        val job = GlobalScope.launch {
//            val data = producer()
//            data.collect {
//                Log.d("TAG", "onCreate 1: $it")
//            }
//        }

//        GlobalScope.launch(Dispatchers.Main){
//            producer()
//                .onStart {
//                    emit(-1)
//                    Log.d("TAG", "onCreate has started")
//                }
//                .onCompletion { Log.d("TAG", "onCreate has completed")
//                emit(82)}
//                .onEach { Log.d("TAG","about to emit - $it") }
//                .collect{Log.d("TAG","has emitted - $it")}
//        }
//        GlobalScope.launch(Dispatchers.Main) {
//            val time = measureTimeMillis {
//                producer() // Define this function to return Flow<Int>
//                    .map {
//                        delay(1000)
//                        println("Map thread - ${Thread.currentThread().name}")
//                        it * 2
//                    }
//                    .filter {
//                        delay(500)
//                        println("Filter thread - ${Thread.currentThread().name}")
//                        it < 8
//                    }
//                    .flowOn(Dispatchers.IO) // Processing before flowOn runs on IO
//                    .collect { value ->
//                        println("Collector thread is ${Thread.currentThread().name}")
//                        delay(1500)
//                        Log.d("Collector", "$value")
//                    }
//            }
//            Log.d("TAG", "Time taken: $time ms")
//        }

//        GlobalScope.launch(Dispatchers.Main) {
//            val time = measureTimeMillis {
//                producer()
//                .map {
//                   delay(1000)
//                    it * 2
//                println("Map thread - ${Thread.currentThread().name}")
//                }
//                    .filter {
//                        delay(500) // Simulate some processing
//                        println("Filter thread - ${Thread.currentThread().name}")
//                        it < 8 // Keep values less than 8
//                    }
////                .collect {
////                    Log.d("ndjsnd", "$it")
////                }
////        }
//                  //  .buffer(3) //through buffer the collection is done.
//                    .flowOn(Dispatchers.IO)
//                    //all down this flowON method runs only on above flow
//                    .collect {
//                        println("collector thread is ${Thread.currentThread().name}")
//                        delay(1500)
//                        Log.d("cjsj", "$it")
//                    }
//            }
//            Log.d("TAG", "so time taken is : $time")
//        }
//        GlobalScope.launch {
//            delay(3000)
//            job.cancel()
//        }
//        val js = GlobalScope.launch {
//            val data = producer()
//            delay(2500)
//            data.collect {
//                Log.d("TAG", "onCreate 2: $it")
//            }
//        }
    }

    private fun consumer() {
//        CoroutineScope(Dispatchers.Main).launch {
//            channel.send(1)
//            channel.send(2)
//            channel.send(3)
//            channel.send(4)
//            channel.close()
//        }
    }


    private fun producer() : Flow<Int> = flow<Int> {
        //flows itself create coroutines scopes
//        CoroutineScope(Dispatchers.Main).launch {
//           Log.d("TAG", "producer: ${channel.receive()}")
//            Log.d("TAG", "producer: ${channel.receive()}")
//            Log.d("TAG", "producer: ${channel.receive()}")
//            Log.d("TAG", "producer: ${channel.receive()}")
//        }
       // withContext(Dispatchers.IO){
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        list.forEach {
            delay(1000)
            println("emitter thread is ${Thread.currentThread().name}")
            emit(it)
        }
        }

//    private fun producers() : MutableSharedFlow<Int>{
//        val mutableSharedFlow = MutableSharedFlow<Int>()
//        GlobalScope.launch {
//            val list = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)
//            list.forEach{
//                mutableSharedFlow.emit(it)
//                println("emitting is -$it")
//           delay(1000)
//
//            }
//        }
//        return mutableSharedFlow
//    }

    private fun producers() : StateFlow<Int>{
        val mutableStateFlow = MutableStateFlow(10)
        GlobalScope.launch {
            delay(2000)
            mutableStateFlow.emit(20)
            delay(2000)
            mutableStateFlow.emit(40)
        }
        return mutableStateFlow.asStateFlow()
    }

    }
