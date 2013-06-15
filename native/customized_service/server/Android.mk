LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
        CustomizedServer.cpp

LOCAL_SHARED_LIBRARIES := \
        libcustomized \
        libutils \
        libbinder

LOCAL_MODULE:= customizedserver

include $(BUILD_EXECUTABLE)
