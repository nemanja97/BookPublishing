import axios from "axios";

export const BookService = { search, download, findReviewers };

export async function search(searchDTO) {
  return await axios.get(`http://localhost:8080/api/search/books`, {
    params: {
      ...searchDTO,
    },
  });
}

export async function download(bookId) {
  return await axios.get(`http://localhost:8080/api/books/${bookId}/download`);
}

export async function findReviewers(searchDTO) {
  return await axios.get(
    `http://localhost:8080/api/search/appropriateReviewers`,
    {
      params: {
        ...searchDTO,
      },
    }
  );
}
