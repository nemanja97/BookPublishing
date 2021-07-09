import axios from 'axios';
import { removeToken } from './TokenService';

export const AuthenticationService = [_login, _logout];

export async function _login(credentials) {
	const data = {
		...credentials,
	};
	return await axios.post(`${process.env.REACT_APP_API_URL}/auth`, data);
}

export function _logout() {
	removeToken();
}
