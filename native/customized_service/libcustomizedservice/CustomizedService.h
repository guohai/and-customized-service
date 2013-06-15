#ifndef ANDROID_SERVERS_TEST_CUSTOMIZEDSERVICE_H
#define ANDROID_SERVERS_TEST_CUSTOMIZEDSERVICE_H

#include <utils/RefBase.h>
#include <binder/IInterface.h>
#include <binder/Parcel.h>

#include <utils/threads.h>

namespace android {

class CustomizedService : public BBinder
{
        public:
        static int instantiate();

        CustomizedService();

        virtual ~CustomizedService();

        virtual status_t onTransact(uint32_t, const Parcel&, Parcel*, uint32_t);

};

}; //namespace

#endif
