import axios from 'axios';
import { getToken } from './TokenService';

export const BookService = { getAll };

export async function getAll(credentials) {
	return await axios.get(`${process.env.REACT_APP_API_URL}/books`, { headers: { 'X-Auth-Token': getToken() } });
}

export async function _getMembershipStatus() {
	return await axios.get(`${process.env.REACT_APP_API_URL}/books/hasMembership`, {
		headers: { 'X-Auth-Token': getToken() },
	});
}

