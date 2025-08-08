package com.example.coroutines

//import kotlinx.coroutines.flow.internal.NopCollector.emit
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.Thread.currentThread
import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.measureTimeMillis


class MainActivity : androidx.appcompat.app.AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         main()
        //custom()
  //      mains()
    //    l()
  //  m()
       //k()
    //  z() <activity
     //  ks()
   //t()
        //nested()
        //except()
 //      contextual()
    //  network()
      // zt()
        advanceException()
     // multiJob()
       // asynComp()
      //  nestedAsync()
      // cancasync()
     //   asyncontext()
     //   awaitAllAsync()4

//        val numberFlow : Flow<Int> = flow{
//            repeat(60){
//                it-> emit(it+1)
//                delay(1000)
//            }
//        }
//
//        lifecycleScope.launch{
//            numberFlow.collect{it->
//                println("the number is ${it.toString()}")
//            }
//        }


//        CoroutineScope(Dispatchers.Main).launch{
//
//                // Both withContext blocks use the same dispatcher (Dispatchers.IO in this case)
////                val time=TimeUtils.getTimeZone()
//                val msgOne = withContext(Dispatchers.IO) { getMessageOne() }
//                val msgTwo = withContext(Dispatchers.IO) { getMessageTwo() }
//                Log.d("tag", "the statement is ${msgOne} ${msgTwo}")
//                val msgThree= withContext(Dispatchers.Main){getMessageThree()}
//                Log.d("tag", "the statement is ${msgOne} ${msgTwo} ${msgThree}")
////            }
////            Log.d("tag", "time completed is $time")
//        }
        //what happens here is that k() takes 2008 ms to work , but z() takes 1008 ms to works as the work is done in parallel
    }

    fun mains() = runBlocking {  //executes in the main thread
//runBlocking {//creates a coroutine that blocks the current main thread
        Log.d("tag", "main program starts ${currentThread().name}") //main thread
        val JobDeferred: Deferred<String> = async { // creates a background coroutine that runs on a background thread
            Log.d("tag", "work starts ${currentThread().name}") //Thread T1
            mySuspendFunc(1000) //coroutine is suspended, but thread T1 is free(not blocked)
            Log.d("tag", "work ends ${currentThread().name}") //Either T1 or some other thread
            //some work is going on in the background thread
            "jndj"
        }
        // blocks the main thread & wait for coroutine to finish (practically not a right way to wait) Thread(200)
        //runBlocking { // creates a coroutine that blocks the main thread
        // mySuspendFunc(2000) //main thread:  wait for coroutine to finish(practically nto a right way to wait)
//JobDeferred.join()
        val num: String = JobDeferred.await()
        println(num)
        Log.d("tag", "main preogram ends ${currentThread().name}") //main thread
    }
    suspend fun mySuspendFunc(time: Long) { //coroutine is suspended but thread is free
        delay(time) //coroutine is suspended but thread is free
    }

    fun l() = runBlocking {
        Log.d("tag", "main program starts ${currentThread().name}") //main thread
        val job: Job = launch(Dispatchers.Default) {
            Log.d("tag", "check the thread ${currentThread().name}")     //main thread")
            try {
                for (i in 0..500) {
                    Log.d("tag", "$i")
                    delay(5)
                }
            } catch (ex: CancellationException) {
                Log.d("tag", "${ex.message}")
                ex.printStackTrace()
                Log.d("tag", "cancelled ${currentThread().name}")
            } finally {
                withContext(NonCancellable) {//this will not cancel the coroutine
                    delay(1000) //this will not cancel the coroutine
                    Log.d("tag", "eyeteeth") //this will be printed after the cancellation
                }
            }
        }
        delay(10)
        job.cancel(CancellationException("my own crash"))
        job.join()
        Log.d("tag", "main preogram ends ${currentThread().name}") //main thread
    }

    private fun m() = runBlocking {
        Log.d("tag", "main program starts ${currentThread().name}") //main thread")
        try {
            val res: String? = withTimeoutOrNull(2000) {
                for (i in 0..500) {
                    Log.d("tag", "$i")
                    delay(500)
                }
                "hey whatsppin"
            }
        } catch (ex: TimeoutCancellationException) {
            Log.d("tag", "caught")
        } finally {
            Log.d("tag", "cancelled ${currentThread().name}")
        }
        //Since we're using withTimeoutOrNull (not withTimeout), the exception is caught internally and returns null instead of propagating.
        //
        //The catch block won't execute because withTimeoutOrNull handles the exception internally.
        //
        //The finally block executes after timeout.
//Log.d("tag",res) // here null is printed because timeout happened as timeout 2k < 0.5k*500 = 2.5k
        Log.d("tag", "main program ends ${currentThread().name}")
    }

    fun z() = runBlocking {
        // it is for sequential navigation (one after another)
        Log.d("tag", "the program starts ${currentThread().name}")

        val time = measureTimeMillis {
            val msgOne: Deferred<String> = async(Dispatchers.Default) {
                Log.d("tag", "the scope is ${this.coroutineContext}")
                Log.d("tag", "the first coroutine starts ${currentThread().name}")
                getMessageOne()
                // Log.d("tag","the first coroutine ends ${currentThread().name}")
            }
            //you can write your own code here

            val msgTwo: Deferred<String> = async {
                Log.d("tag", "the scope is ${this.coroutineContext}")
                Log.d("tag", "the second coroutine starts ${currentThread().name}")
                getMessageTwo()
            }
            //similar to the msgOne

            Log.d("tag", " the statement is ${msgOne.await()} ${msgTwo.await()}")
        }
        Log.d("tag", "time completed is $time")
        Log.d("tag", "the program ends ${currentThread().name}")
//        }
    }


    fun k() = runBlocking { // it is for sequential navigation (one after another)
        Log.d("tag", "the program starts ${currentThread().name}")

        val time = measureTimeMillis {
            val msgOne = getMessageOne()
            val msgTwo = getMessageTwo()
            Log.d("tag", " the statement is $msgOne $msgTwo")
        }
        Log.d("tag", "time completed is $time")
        Log.d("tag", "the program ends ${currentThread().name}")
    }

    suspend fun getMessageOne(): String {
        val time = measureTimeMillis {
            delay(1000L) //pretend to do some work
//           return "Hello"
        }
        return time.toString()
    }

    suspend fun getMessageTwo(): String {
//          delay(1000L) //pretend to do some work
//            return "World"
        val time = measureTimeMillis {
            delay(1000L) //pretend to do some work
//           return "Hello"
        }
        return time.toString()
    }

    suspend fun getMessageThree(): String {
//        delay(1000L) //pretend to do some work
//        Log.d("TAG", "getMessageThree: hii")
//        return "kotlin"
        val time = measureTimeMillis {
            delay(1000L) //pretend to do some work
//           return "Hello"
        }
        return time.toString()
    }

    fun t() = runBlocking { // blocking coroutine
        Log.d("tag", "run blocking -> $this")

        launch {  //standalone coroutine
            delay(1000)
            Log.d("tag", "launch -> $this")

            launch {
                delay(500)  // parent or child has different coroutine scope instance
                Log.d("tag", "launchs->$this")
            }
        }

        async {
            delay(2000) //deferred coroutine
            Log.d("tag", "async -> $this")
        }

    }

    fun zt() = runBlocking {
        val supervisor = SupervisorJob()
        val customContext = Dispatchers.Default + supervisor
        val parentScope = CoroutineScope(customContext)
        val childjob1 = parentScope.launch(CoroutineName("child1")) {
            println("child1 is running on ${Thread.currentThread().name}")
            delay(100)
            throw Exception("child1 failed")
        }
        //even if the childjob1 failed, child 2 continued and ended due to the supervisor scope.
        val childjob2 = parentScope.launch(CoroutineName("child2")) {
            println("child2 is running on ${Thread.currentThread().name}")
            delay(200)
            println("ChildCoroutine2 completed successfully")
        }
        joinAll(childjob1, childjob2)
        println("All scopes done and now the thread is ${Thread.currentThread().name}")
    }

    fun nested() = runBlocking {
        val parentJob = Job()

        val parentContext = parentJob + Dispatchers.Default + CoroutineName("Parent")
        val parentScope = CoroutineScope(parentContext)

        val parent = parentScope.launch {
            println("the given context and thread is ${parentScope.coroutineContext} + ${Thread.currentThread().name}")

            val child1 = withContext(CoroutineName("ChildCoroutine1")) {
                println("the given context and thread is ${coroutineContext} + ${Thread.currentThread().name}")
                delay(1000)
                println("the child1 has ended")
            }
            withContext(CoroutineName("ChildCoroutine2")) {
                try {
                    println("the given context and thread is ${coroutineContext} + ${Thread.currentThread().name}")
                    delay(2000)
                    println("the child2 has ended")
                }
          catch (CancellationException: Exception) {
              println("the child2 has been cancelled")
          }
          }
        }

        delay(3000)
        println("cancelling the parent")
        parentJob.cancel()
        parentJob.join()
        println("parent is cancelled")

    }
    private fun except() = runBlocking {
        // Creating an exception handler
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught exception: ${exception.message} and thread is ${currentThread().name}")
        }
        // Custom context with Job and exception handler
        val customContext = Dispatchers.Default + Job() + handler
        // Launching a coroutine with the custom context
        val job = launch(customContext) {
            println("Running in: ${currentThread().name}")
            // Launch a child coroutine that throws an exception
           val kss =  launch {
                println("the thread running is ${currentThread().name} and coroutine is ${coroutineContext}")
                println("Child coroutine is about to throw an exception")
                throw Exception("Something went wrong in child coroutine!")
            }
            // Delay to give the child coroutine time to fail
            delay(1000)
            println("Parent coroutine finished")
        }
        delay(10000)
        job.join()
        println("All coroutines completed.")
    }

    fun custom() = runBlocking {
        val customJob = Job()
        val customContext =
            customJob + Dispatchers.Default + CoroutineName("CustomCoroutineTimeout")
        val job = launch(customContext) {
            try {
                withTimeout(3000) {
                    println("the coroutine has started and running on ${Thread.currentThread().name}")

                    repeat(5) { i ->
                        println("Working... $i")
                        delay(1000)
                    }

                    println("the coroutine has ended")
                }
            } catch (e: TimeoutCancellationException) {
                println("the timeout occured leading to cancellation of the coroutine")
            }
        }
        job.join()
        println("main coroutine has ended")
    }
//
//    private fun contextual() = runBlocking{   // EVEN IF PARENTJOB IS CANCELLED, IF SUPERVISERJOB() DOESN'T HAVE ANY PARAM OF PARENTJOB THEN IT DOESNT GET CANCELLED

//        val parenob = Job()
//        val parentscope = CoroutineScope(parenob + Dispatchers.Default + CoroutineName("ParentScope"))
//
//        val par = parentscope.launch {
//            println("the parent is running on ${currentThread().name}")
//            println("the parent is launched")
//
//            val child1 = launch(CoroutineName("child1")) {
//                println("coroutine has started on ${currentThread().name}")
//                delay(2000)
//                println("coroutine 1 has ended")
//            }
//
//            val supervisor = SupervisorJob()
//            val child2 = launch(supervisor + CoroutineName("child2")) {
//                println("coroutine has started on ${currentThread().name}")
//                delay(10000)
//                println("coroutine 2 has ended")
//            }
//
//            val child3 = launch(CoroutineName("child3")) {
//                println("the child coroutine has tarted on ${currentThread().name}")
//                delay(3000)
//                println("the child 3 coroutine has ended")
//            }
//
//            try {
//                joinAll(child1, child2, child3)
//                println("the work has ended")
//
//            } catch (e: Exception) {
//                println("couldn't run all the coroutines")
//            }
//
//        }
//            delay(2500)
//            parenob.cancel()
//            par.join()
//            println("the job gets finished")
//
//    }
//
//        private fun network() = runBlocking {
//            val applicationJob = Job()
//            val networkJob = Job()
//
//            val appScope = CoroutineScope(applicationJob + Dispatchers.Default + CoroutineName("App"))
//            val networkScope = CoroutineScope(networkJob + Dispatchers.IO + CoroutineName("Network"))
//
//            val child = appScope.launch {
//                println("the application started in thread name ${currentThread().name}")
//
//             val net = networkScope.launch {
//                 println("Network request has started in ${currentThread().name}")
//                 delay(20000)
//                 println("Network request has ended in ${currentThread().name}")
//             }
//                net.join()
//                delay(4000)
//                println("the netrequest has ended")
//            }
//            delay(3000)
//            println("cancelling netwrok job")
//            networkJob.cancel()
//            child.join()
//            println("the application has ended")
//        }

    fun advanceException() = runBlocking {

        val handler = CoroutineExceptionHandler{_,exception->
            println("Caught exception: ${exception.message}")
        }
        val job = CoroutineScope(Dispatchers.Default + Job() + handler).launch {
            println("the job has started")
            val superviserScope = CoroutineScope(SupervisorJob() + handler)
            //HERE EVERY JOB IS IRRESPECTIVE OF THAT OF EACH OTHER. EVEN IF ONE JOB FAILS, THE OTHER JOBS WILL CONTINUE TO RUN
            val job1 = superviserScope.launch {
                println("the job1 started at theead ${Thread.currentThread().name}")
                delay(500)
                throw Exception("job1 failed")
            }

            val job2 = superviserScope.launch {
                println("the job2 has started at thread ${Thread.currentThread().name}")
                delay(1000)
                println("the job2 has ended")
            }

            val job3 = superviserScope.launch {
                println("the job3 has started at thread ${Thread.currentThread().name}")
                delay(1500)
                println("the job3 has ended")
            }
        }

        delay(100)
        job.cancel()
        println("the job has ended")

        try{
//            joinAll(job1,job2,job3)
            println("the all jobs have ended")
        }
        catch (e : Exception){
            println("caught exception at parent scope ${e.message}")
        }

        println("the parent scope has ended")

    }

    fun multiJob() = runBlocking {

        val systemJob = Job()
        val systemScope = CoroutineScope(systemJob)
        val componentJob = Job()
        val componentScope = CoroutineScope(componentJob + Dispatchers.IO)

        val job1 = systemScope.launch {
        println("the system has launcehd")

            val job2 = componentScope.launch {
                println("some job at thread ${Thread.currentThread().name}")
                delay(1000)
                println("the job2 has ended")
            }

            job2.join()
            println("the job2 has been subdued")
     }
        delay(500)
        systemJob.cancel()

        job1.join()
        println("the system has ended")
    }

    fun asynComp() = runBlocking {

            val deferred1 = async{
                delay(1000)
                println("defferd1complete")
                10
            }
            val deferred2 = async{
                val res1 = deferred1.await()
                println("the deffered 2 recieved result frm deferred1 : $res1")
                delay(1000)
                res1*2
            }

            val deferred3 = async{
                val res2 = deferred2.await()
                println("deferrred 3 recieved result from deferred 2 :$res2")
                delay(1000)
                res2 + 10
            }
            deferred3.await()
        println("the deferred3 has ended with result ${deferred3.await()}")
    }

    fun cancasync() = runBlocking {
        val job = launch {
            val deferred = async {
                repeat(5) { i ->
                    println("tasl $i is running")
                    delay(500)
                }
                10
            }

            val deferred2 = async{
                delay(500)
                println("deferred2 is completeed")
                200
            }
            try {
                withTimeout(2000) {
                   val result = awaitAll((deferred),(deferred2))
                    println("Results is $result")
                }
            }
            catch (e: TimeoutCancellationException){
                println("the timeout has occured")
            }

        }
        job.join()
        println("program is cancelled")
    }
//        delay(1000)
//        println("the cancasync is cancelled")
//        deferred.cancel()

//        try{
//            deferred.await()
//        }
//        catch (e: Exception){
//            println("task was cancelled")
//        }
//    }
//
//    fun awaitAllAsync() = runBlocking {
//
//        val deferred1 = async{
//            delay(1000)
//            println("the 1st is done")
//            1
//        }
//
//        val deferred2 = async{
//            delay(2000)
//            println("2nd")
//            2
//        }
//
//        val deferred3 = async {
//            delay(1000)
//            println("the 3rd is done")
//            3
//        }
//
//        try{
//            joinAll(deferred1,deferred2,deferred3)
//        }
//        catch (e : Exception){
//            println("the await didn;t work")
//        }
//
//    }

    fun main(){
        val flow = flow {
            emit(1)
            delay(100)
            emit(2)
        }

        val job = lifecycleScope.launch {
            flow.collectLatest {
                delay(150)
                println(it)
            }
        }

    }

    fun nestedAsync() = runBlocking{
    val outer = async{
        val innerone = async{
            delay(1000)
            10
        }
        val ineer2 = async{
            delay(240)
            20
        }

        val result = innerone.await() + ineer2.await()
        result
    }
        val finalres = outer.await()
        println("the finalres is $finalres")
    }

    private suspend fun performIOTask(string: String) : Int {
      delay(500)
        return 10
    }

    private suspend fun performCPUTask(string: String) : Int{
        delay(1000)
        return 20
    }
    fun asyncontext() = runBlocking {
        val result = async(Dispatchers.Default){
            val ioresult = async(Dispatchers.IO){
                performIOTask("ed")
                }
            val cpuRes = async(Dispatchers.Default){
                performCPUTask("dd")
            }

            awaitAll(ioresult,cpuRes)
        }
        println("final result is ${result.await().get(0)}")
    }

}

