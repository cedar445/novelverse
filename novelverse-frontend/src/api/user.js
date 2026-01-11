import request from './request'

// get
export const getUsers = async () => {
  const res = await request.get('/users')
  return res
}

export const getUserById = async (id) => {
  const res = await request.get(`/users/${id}`)
  return res
}

export const getUserByName = async (name) => {
  const res = await request.get(`/users`, {
    params: { name },
  })
  return res
}

// post
export const insertUser = async (user) => {
  const res = await request.post('/users', user)
  return res
}

// put
export const updateUser = async (user) => {
  const res = await request.put('/users', user)
  return res
}

//delete
export const deleteUser = async (id) => {
  const res = await request.put(`/users/${id}`)
  return res
}
