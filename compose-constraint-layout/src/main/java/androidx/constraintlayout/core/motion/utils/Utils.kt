/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.constraintlayout.core.motion.utils

class Utils constructor() {
    fun getInterpolatedColor(value: FloatArray): Int {
        val r: Int = clamp(
            (Math.pow(value.get(0).toDouble(), 1.0 / 2.2).toFloat() * 255.0f).toInt()
        )
        val g: Int = clamp(
            (Math.pow(value.get(1).toDouble(), 1.0 / 2.2).toFloat() * 255.0f).toInt()
        )
        val b: Int = clamp(
            (Math.pow(value.get(2).toDouble(), 1.0 / 2.2).toFloat() * 255.0f).toInt()
        )
        val a: Int = clamp((value.get(3) * 255.0f).toInt())
        val color: Int = (a shl 24) or (r shl 16) or (g shl 8) or b
        return color
    }

    open interface DebugHandle {
        fun message(str: String?)
    }

    companion object {
        /*
    public static void log(String tag, String value) {
        System.out.println(tag+" : "+value);
    }
    public static void loge(String tag, String value) {
        System.err.println(tag+" : "+value);
    }*/
        /*
   public static void socketSend(String str) {
       try {
           Socket socket = new Socket("127.0.0.1", 5327);
           OutputStream out = socket.getOutputStream();
           out.write(str.getBytes());
           out.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }*/
        private fun clamp(c: Int): Int {
            var c: Int = c
            val N: Int = 255
            c = c and (c shr 31).inv()
            c -= N
            c = c and (c shr 31)
            c += N
            return c
        }

        fun rgbaTocColor(r: Float, g: Float, b: Float, a: Float): Int {
            val ir: Int = clamp((r * 255f).toInt())
            val ig: Int = clamp((g * 255f).toInt())
            val ib: Int = clamp((b * 255f).toInt())
            val ia: Int = clamp((a * 255f).toInt())
            val color: Int = (ia shl 24) or (ir shl 16) or (ig shl 8) or ib
            return color
        }

        var ourHandle: DebugHandle? = null
        fun setDebugHandle(handle: DebugHandle?) {
            ourHandle = handle
        } /*
    public static void logStack(String msg, int n) {
        StackTraceElement[] st = new Throwable().getStackTrace();
        String s = " ";
        n = Math.min(n, st.length - 1);
        for (int i = 1; i <= n; i++) {
            StackTraceElement ste = st[i];
            String stack = ".(" + st[i].getFileName() + ":" + st[i].getLineNumber() + ") " + st[i].getMethodName();
            s += " ";
            System.out.println(msg + s + stack + s);
        }
    }*/
        /*
    public static void log(String str) {

        StackTraceElement s = new Throwable().getStackTrace()[1];
        String methodName =  s.getMethodName();
        methodName = (methodName+"                  ").substring(0,17);
        String  npad = "    ".substring(Integer.toString(s.getLineNumber()).length());
        String ss = ".(" + s.getFileName() + ":" + s.getLineNumber() + ")" + npad +methodName;
        System.out.println(ss + " " + str);
        if (ourHandle != null) {
            ourHandle.message(ss + " " + str);
        }
    }
*/
    }
}