// 简单的使用示例
'use strict';
const uniPush = uniCloud.getPushManager({appId:"__UNI__F607BFD"}) //注意这里需要传入你的应用appId，用于指定接收消息的客户端

// 云函数url化使用，相当于开启了一个接口，可以请求这个接口发送推送。
exports.main = async (event, context) => {
	// 这一步是为了适配 uniCloud.callFunction 云函数的方式调用
	if(event.clientID && event.title && event.content){
		return await uniPush.sendMessage({
			"push_clientid": event.clientID,
			"title": event.title,	
			"content": event.content,
			"payload": event.payload,
			"badge":"+1"
		})
	}
	let data = JSON.parse(event.body)
	return await uniPush.sendMessage({
		"push_clientid": data.clientID,
		"title": data.title,	
		"content": data.content,
		"payload": data.payload,
		"badge":"+1"
	})
};

// // //本地测试使用
// exports.main = async (event, context) => {
// 	return await uniPush.sendMessage({
// 		"push_clientid": "b00c8602d627bca826a26b74b1f291ad", 	//填写上一步在uni-app客户端获取到的客户端推送标识push_clientid
// 		"title": "通知栏显示的标题",	
// 		"content": "通知栏显示的内容",
// 		"payload": {
// 			"text":"体验一下uni-push2.0"
// 		}
// 	})
// };