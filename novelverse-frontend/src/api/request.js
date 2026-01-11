import axios from 'axios'

// 创建 axios 实例
const request = axios.create({
  // 后端接口地址
  baseURL: 'http://localhost:8080/novelverse-backend',
  timeout: 5000,
})

// 请求拦截器：在请求发出前执行
request.interceptors.request.use(
  (config) => {
    // 从本地存储中读取 token
    const token = localStorage.getItem('token')
    // 如果 token 存在，则统一加到请求头中
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
      // console.log('已添加token到请求头' + token)
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

// 响应拦截器：在响应返回后执行
request.interceptors.response.use(
  (response) => {
    // 后端返回的数据
    const res = response.data
    // 根据后端约定判断是否成功
    if (String(res.code).endsWith('0')) {
      console.error(res.msg || '请求失败')
      return Promise.reject(res)
    }
    //返回res,不在拦截的时候处理.data
    return res
  },
  (error) => {
    // HTTP 层面的错误
    if (error.response) {
      const status = error.response.status
      if (status === 401) {
        // token 失效或未登录
        console.warn('登录状态失效，请重新登录')
        // 清除本地 token
        localStorage.removeItem('token')
        // 这里可以按需跳转登录页
        //
      } else if (status === 500) {
        console.error('服务器内部错误')
      } else {
        console.error('请求异常：', error.response.data)
      }
    }

    return Promise.reject(error)
  },
)

export default request
