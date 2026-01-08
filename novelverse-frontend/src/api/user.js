import request from './request'

export const test = () => {
  console.log(request.get('/user'))
}
