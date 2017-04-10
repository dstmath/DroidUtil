package com.dst.droidlib.hook;


import com.dst.droidlib.reflect.Reflect;

import android.os.IBinder;
import android.os.IInterface;

/**
 * @author Paulo Costa
 *
 * @see MethodInvocationProxy
 */
public abstract class BinderInvocationProxy extends MethodInvocationProxy<BinderInvocationStub> {

	protected String mServiceName;

	public BinderInvocationProxy(IInterface stub, String serviceName) {
		this(new BinderInvocationStub(stub), serviceName);
	}

	/*public BinderInvocationProxy(RefStaticMethod<IInterface> asInterfaceMethod, String serviceName) {
		this(new BinderInvocationStub(asInterfaceMethod, ServiceManager.getService.call(serviceName)), serviceName);
	}*/

	public BinderInvocationProxy(Class<?> stubClass, String serviceName) {
		this(new BinderInvocationStub(stubClass, (IBinder) Reflect.on("android.os.ServiceManager").call("getService", serviceName).get()), serviceName);
	}

	public BinderInvocationProxy(BinderInvocationStub hookDelegate, String serviceName) {
		super(hookDelegate);
		this.mServiceName = serviceName;
	}

	@Override
	public void inject() throws Throwable {
		getInvocationStub().replaceService(mServiceName);
	}

	@Override
	public boolean isEnvBad() {
		IBinder binder = Reflect.on("android.os.ServiceManager").call("getService", mServiceName).get();
//		IBinder binder = ServiceManager.getService.call(mServiceName);
		return binder != null && getInvocationStub() != binder;
	}
}
