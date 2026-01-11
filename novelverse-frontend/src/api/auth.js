import request from './request'

// post
export const login = async (user) => {
  const res = await request.post('/auth/login', user)
  return res
}

export const register = async (user) => {
  const res = await request.post('/auth/register', user)
  return res
}
