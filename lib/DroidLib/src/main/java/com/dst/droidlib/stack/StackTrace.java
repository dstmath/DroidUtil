package com.dst.droidlib.stack;

/**
 * Created by toor on 10/28/16.
 */

public class StackTrace {


    /**
     * new Throwable().getStackTrace()
     */
    public static Info getMethodInfo() {
        StackTraceElement[] sElements = new Throwable().getStackTrace();
        Info info = new Info(sElements[1].getFileName(), sElements[1].getMethodName(), sElements[1].getLineNumber());
        return info;
    }

    public static class Info {
        String className;
        String methodName;
        int lineNumber;

        public Info(String className, String methodName, int lineNumber) {
            this.className = className;
            this.methodName = methodName;
            this.lineNumber = lineNumber;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "className='" + className + '\'' +
                    ", methodName='" + methodName + '\'' +
                    ", lineNumber=" + lineNumber +
                    '}';
        }
    }
}
