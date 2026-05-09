<template>
	<view class="content">
		
		<view>
			<text>结果：{{scanResult}}</text>
		</view>
		<button @click="scan">扫码</button>
		<button @click="create">生成</button>
		<button @click="checkSelfPermission">权限</button>
		<image :src="img"></image>
	</view>
</template>


<script>
	// import { ref } from 'vue'
	import { scanCode, createQRCode, checkSelfPermission } from '@/uni_modules/lime-scan';
	
	export default {
		data() {
			return {
				scanResult: '',
				img: ''
			}
		},
		methods: {
			checkSelfPermission() {
				// media 根据条件会选择 photo 或 storage
				// photo 选择 photo 
				// storage 选择 storage 
				checkSelfPermission(['media']).then(res => {
					console.log('申请 media 权限成功', res)
				})
			},
			create() {
				createQRCode({
					content: 'lime.qcoon.cn',
					success: (res) => {
						this.img = res
						console.log('res', res)
					}
				})
			},
			scan(){
				try {
					scanCode({
						multiScan: true,
						scanMode: 'custom', 
						// scanType: ['barCode'],
						success:(res) => {
							const result = res.result
							this.scanResult = result
							// uni.showToast({
							// 	title: result
							// })
						},
						fail: (err) =>{
							this.scanResult = '扫码失败'
							console.log('err', err)
						}
					})
				} catch(err) {
					console.log('err', err)
				}
			}
		}
	}
	
</script>

<style>

</style>