package com.ruoshui.rpc.remoting.net.impl.netty.client;

import com.ruoshui.rpc.remoting.net.Client;
import com.ruoshui.rpc.remoting.net.common.ConnectClient;
import com.ruoshui.rpc.remoting.net.params.XxlRpcRequest;

/**
 * netty client
 *
 * @author xuxueli 2015-11-24 22:25:15
 */
public class NettyClient extends Client {

	private Class<? extends ConnectClient> connectClientImpl = NettyConnectClient.class;

	@Override
	public void asyncSend(String address, XxlRpcRequest xxlRpcRequest) throws Exception {
		ConnectClient.asyncSend(xxlRpcRequest, address, connectClientImpl, xxlRpcReferenceBean);
	}

}
