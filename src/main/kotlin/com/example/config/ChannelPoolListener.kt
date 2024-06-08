package com.example.config

import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.Channel
import io.micronaut.context.annotation.Value
import io.micronaut.rabbitmq.connect.ChannelInitializer
import jakarta.inject.Singleton
import java.io.IOException

@Singleton
class ChannelPoolListener : ChannelInitializer(
) {

    @Throws(IOException::class)
    override fun initialize(channel: Channel, name: String){
        channel.exchangeDeclare("exchangeAppUserTest", BuiltinExchangeType.DIRECT, true)
        channel.queueDeclare("queueAppUserTest", true, false, false, null)
        channel.queueBind("queueAppUserTest", "exchangeAppUserTest", "keyAppUserTest")
    }
}
