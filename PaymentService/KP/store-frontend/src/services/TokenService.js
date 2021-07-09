import jwtDecode from 'jwt-decode';

export const getToken = () => {
	return localStorage.getItem('token');
};

export const setToken = (value) => {
	localStorage.setItem('token', value);
};

export const removeToken = () => {
	localStorage.removeItem('token');
};

export const decodeToken = (token) => {
	try {
		return jwtDecode(token);
	} catch (error) {
		return null;
	}
};
