package me.prince.kotlincachedemo

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.TimeUnit


@SpringBootApplication
@EnableCaching
class KotlinCacheDemoApplication {
    @Bean
    fun caffeineConfig(): Caffeine<*, *> {
        return Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS)
    }
}


fun main(args: Array<String>) {
    runApplication<KotlinCacheDemoApplication>(*args)
}


@RestController
class CacheController {
    @Autowired
    lateinit var cacheService: CacheService

    @GetMapping("/cache/{id}")
    fun callCache(@PathVariable id: Int): String {
        return cacheService.invoke(id);
    }
}

@Service
class CacheService {
    @Cacheable("kotlinCache")
    fun invoke(id: Int): String {
        println("hello")
        val nextInt = Random().nextInt()
        return "${id}_${nextInt}"
    }
}