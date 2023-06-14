package com.jet.jetnet

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.jet.jetnet.RetrofitHelper.handleResult
import com.jet.jetnet.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        RetrofitHelper.setBaseUrl("https://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com")
        val testService = RetrofitHelper.createService(TestApi::class.java)
        viewBinding.tvTest.setOnClickListener {
            lifecycleScope.launch {
                RetrofitHelper
                    .executeRequest { testService.getBaidu().execute() }
                    .handleResult(
                        successHandler = {
                            Toast.makeText(this@MainActivity, "调用成功", Toast.LENGTH_SHORT).show()
                        },
                        errorHandler = {
                            Toast.makeText(this@MainActivity, "调用失败", Toast.LENGTH_SHORT).show()
                        })
            }
        }
    }
}