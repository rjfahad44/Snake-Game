package com.example.snakegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.location.GnssStatusCompat.ConstellationType
import com.example.snakegame.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val meat = ImageView(this)
        val snake = ImageView(this)
        val snakeSegments = mutableListOf(snake) // Keep track of the position of each snake segment
        val handler = Handler(Looper.getMainLooper())
        var delayMillis = 30L // Update snake position every 100 milliseconds
        var currentDirection = "right" // Start moving right by default
        var scorex = 0


        binding.board.visibility = View.INVISIBLE
        binding.playagain.visibility = View.INVISIBLE
        binding.score.visibility = View.INVISIBLE
        binding.score2.visibility = View.INVISIBLE

        binding.newGame.setOnClickListener {

            binding.board.visibility = View.VISIBLE
            binding.newGame.visibility = View.INVISIBLE
            binding.resume.visibility = View.INVISIBLE
            binding.score2.visibility = View.VISIBLE


            snake.setImageResource(R.drawable.snake)
            snake.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.board.addView(snake)
            snakeSegments.add(snake) // Add the new snake segment to the list

            var snakeX = snake.x
            var snakeY = snake.y

            meat.setImageResource(R.drawable.meat)
            meat.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.board.addView(meat)

            val random = Random() // create a Random object
            val randomX = random.nextInt(801) - 400 // generate a random x-coordinate between -400 and 400
            val randomY = random.nextInt(801) - 400 // generate a random y-coordinate between -400 and 400


            meat.x = randomX.toFloat()
            meat.y = randomY.toFloat()


            fun checkFoodCollision() {
                val distanceThreshold = 50

                val distance = sqrt((snake.x - meat.x).pow(2) + (snake.y - meat.y).pow(2))

                if (distance < distanceThreshold) { // Check if the distance between the snake head and the meat is less than the threshold

                    val newSnake = ImageView(this) // Create a new ImageView for the additional snake segment
                    newSnake.setImageResource(R.drawable.snake)
                    newSnake.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    binding.board.addView(newSnake)

                    snakeSegments.add(newSnake) // Add the new snake segment to the list

                    val randomX = random.nextInt(801) - -100
                    val randomY = random.nextInt(801) - -100


                    meat.x = randomX.toFloat()
                    meat.y = randomY.toFloat()


                    delayMillis-- // Reduce delay value by 1
                    scorex++

                    binding.score2.text =   "score : " + scorex.toString() // Update delay text view
                }
            }




            val runnable = object : Runnable {
                override fun run() {

                    for (i in snakeSegments.size - 1 downTo 1) { // Update the position of each snake segment except for the head
                        snakeSegments[i].x = snakeSegments[i - 1].x
                        snakeSegments[i].y = snakeSegments[i - 1].y
                    }

                    when (currentDirection) {
                        "up" -> {
                            snakeY -= 10
                            if (snakeY < -490) { // Check if the ImageView goes off the top of the board
                                snakeY = -490f
                                binding.relativeLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.red))
                                binding.playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                binding.lilu.visibility = View.INVISIBLE

                                binding.score.text =   "your score is  " + scorex.toString() // Update delay text view
                                binding.score.visibility = View.VISIBLE
                                binding.score2.visibility = View.INVISIBLE
                            }
                            snake.translationY = snakeY
                        }
                        "down" -> {
                            snakeY += 10
                            val maxY =
                                binding.board.height / 2 - snake.height + 30 // Calculate the maximum y coordinate
                            if (snakeY > maxY) { // Check if the ImageView goes off the bottom of the board
                                snakeY = maxY.toFloat()
                                binding.relativeLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.red))
                                binding.playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                binding.lilu.visibility = View.INVISIBLE

                                binding.score.text =   "your score is  " + scorex.toString() // Update delay text view
                                binding.score.visibility = View.VISIBLE
                                binding.score2.visibility = View.INVISIBLE


                            }
                            snake.translationY = snakeY
                        }
                        "left" -> {
                            snakeX -= 10
                            if (snakeX < -490) { // Check if the ImageView goes off the top of the board
                                snakeX = -490f
                                binding.relativeLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.red))
                                binding.playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                binding.lilu.visibility = View.INVISIBLE
                                binding.score.text =   "your score is  " + scorex.toString() // Update delay text view
                                binding.score.visibility = View.VISIBLE
                                binding.score2.visibility = View.INVISIBLE
                            }
                            snake.translationX = snakeX
                        }
                        "right" -> {
                            snakeX += 10
                            val maxX =
                                binding.board.height / 2 - snake.height + 30 // Calculate the maximum y coordinate
                            if (snakeX > maxX) { // Check if the ImageView goes off the bottom of the board
                                snakeX = maxX.toFloat()
                                binding.relativeLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.red))
                                binding.playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                binding.lilu.visibility = View.INVISIBLE

                                binding.score.text =   "your score is  " + scorex.toString() // Update delay text view
                                binding.score.visibility = View.VISIBLE
                                binding.score2.visibility = View.INVISIBLE
                            }
                            snake.translationX = snakeX
                        }

                        "pause" -> {
                            snakeX += 0
                            snake.translationX = snakeX
                        }
                    }

                    checkFoodCollision()
                    handler.postDelayed(this, delayMillis)
                }
            }

            handler.postDelayed(runnable, delayMillis)

// Set button onClickListeners to update the currentDirection variable when pressed
            binding.up.setOnClickListener {
                currentDirection = "up"
            }
            binding.down.setOnClickListener {
                currentDirection = "down"
            }
            binding.left.setOnClickListener {
                currentDirection = "left"
            }
            binding.right.setOnClickListener {
                currentDirection = "right"
            }
            binding.pause.setOnClickListener {
                currentDirection = "pause"
                binding.board.visibility = View.INVISIBLE
                binding.newGame.visibility = View.VISIBLE
                binding.resume.visibility = View.VISIBLE

            }
            binding.resume.setOnClickListener {
                currentDirection = "right"
                binding.board.visibility = View.VISIBLE
                binding.newGame.visibility = View.INVISIBLE
                binding.resume.visibility = View.INVISIBLE

            }
            binding.playagain.setOnClickListener {
                recreate()
            }
        }
    }
}