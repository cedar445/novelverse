import request from './request'
import { getUserById } from './user'

// get
export const getBooks = async () => {
  const res = await request.get('/books')
  return res
}

export const getBookById = async (id) => {
  const res = await request.get(`/books/${id}`)
  const d = res.data

  const author = await getUserById(d.author_id)
  // console.log('作者信息', author)

  return {
    id: d.id,
    name: d.name,
    author: author.data.name,
    tags: d.tags,
    description: d.description,
    file_path: d.file_path,
  }
}

// post
export const insertBook = async (book) => {
  const res = await request.post('/books', book)
  return res
}

// put
export const updateBook = async (book) => {
  const res = await request.put('/books', book)
  return res
}

//delete
export const deleteBook = async (id) => {
  const res = await request.delete(`/books/${id}`)
  return res
}

export const uploadLocalTxt = async (formData) => {
  console.log(formData.get('file'))

  const res = await request.post('/books/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
  return res
}
