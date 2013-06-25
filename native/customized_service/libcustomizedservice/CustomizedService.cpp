#include "CustomizedService.h"

#include <binder/IServiceManager.h>
#include <binder/IPCThreadState.h>

#include <utils/Log.h>

namespace android {

int CustomizedService::instantiate()
{
    ALOGD("CustomizedService instantiate");
    int r = defaultServiceManager()->addService(String16("test.customized"), new CustomizedService());
    ALOGD("CustomizedService r = %d", r);
    return r;
}

CustomizedService::CustomizedService()
{
    ALOGD("CustomizedService created");
}

CustomizedService::~CustomizedService()
{
    ALOGD("CustomizedService destroyed");
}

void CustomizedService::testGetServiceInSameProcess()
{
    ALOGD("CustomizedService testGetServiceInSameProcess %d", getpid());
}

status_t CustomizedService::onTransact(uint32_t code, const Parcel &data, Parcel *reply, uint32_t flags)
{
    switch (code)
    {
        case 0:
        {
        
            ALOGD("CustomizedService onTransact");

            // invoke service itself with defaultServiceManager()/getService(String16(...))
            sp<IServiceManager> sm = defaultServiceManager();
            sp<IBinder> binder = sm->getService(String16("test.customized"));
            CustomizedService *service = static_cast<CustomizedService*>(binder.get());

            service->testGetServiceInSameProcess();

            pid_t pid = data.readInt32();
            int seq_num = data.readInt32();
            seq_num = seq_num + 1000;
            reply->writeString16(String16("server got your pid."));
            reply->writeInt32(seq_num);
            return NO_ERROR;
        }
        break;

        default:
            return BBinder::onTransact(code, data, reply, flags);
    }
}

}; //namespace


