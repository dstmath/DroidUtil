package com.dst.droidlib.hook;


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

	public BinderInvocationProxy(String stubClass, String serviceName){
		this(HookHelper.load(stubClass), serviceName);
	}
	public BinderInvocationProxy(Class<?> stubClass, String serviceName) {
		this(new BinderInvocationStub(stubClass, HookHelper.getService(serviceName)), serviceName);
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
		IBinder binder = HookHelper.getService(mServiceName);
		return binder != null && getInvocationStub() != binder;
	}
}
