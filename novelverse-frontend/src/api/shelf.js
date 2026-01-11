import request from './request'

// get
export const getShelf = async () => {
  const res = await request.get('/shelf')
  return res
}
