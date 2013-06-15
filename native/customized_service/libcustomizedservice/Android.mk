LOCAL_PATH:= $(call my-dir)

#
# libcustomized for test
#

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
    CustomizedService.cpp

LOCAL_SHARED_LIBRARIES :=     \
        libcutils             \
        libutils              \
        libbinder             \
        libandroid_runtime

LOCAL_PRELINK_MODULE := false

LOCAL_MODULE := libcustomized

include $(BUILD_SHARED_LIBRARY)
