package @packageName@

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class @projectName@Application

fun main(args: Array<String>) {
	runApplication<@projectName@Application>(*args)
}
