package com.hbs.auto

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener

/**
 * Created by zun.wei on 2023/6/19.
 *
 */
class JianYingHelper implements NativeKeyListener,
        NativeMouseInputListener,
        NativeMouseWheelListener {

    void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        println nativeEvent
    }

    void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        println nativeEvent
    }

    void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        println nativeEvent
    }

    @Override
    void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        println "nativeEvent = $nativeEvent"
    }

    @Override
    void nativeMousePressed(NativeMouseEvent nativeEvent) {
        println "nativeEvent = $nativeEvent"
    }

    @Override
    void nativeMouseReleased(NativeMouseEvent nativeEvent) {
        println "nativeEvent = $nativeEvent"
    }

    @Override
    void nativeMouseMoved(NativeMouseEvent nativeEvent) {
        println "nativeEvent = $nativeEvent"
    }

    @Override
    void nativeMouseDragged(NativeMouseEvent nativeEvent) {
        println "nativeEvent = $nativeEvent"
    }

    @Override
    void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
        println "nativeEvent = $nativeEvent"
    }


    static void main(String[] args) {
        GlobalScreen.registerNativeHook()
        JianYingHelper jianYingHelper = new JianYingHelper()
        GlobalScreen.addNativeKeyListener(jianYingHelper)
        GlobalScreen.addNativeMouseListener(jianYingHelper)
        GlobalScreen.addNativeMouseWheelListener(jianYingHelper)

    }


}
