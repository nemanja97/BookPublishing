import axios from 'axios';
import { getToken } from './TokenService';

export const InvoiceService = { createNewInvoice };

export async function createNewInvoice(dto) {
	return await axios.post(`${process.env.REACT_APP_API_URL}/invoice`, dto, {
		headers: { 'X-Auth-Token': getToken() },
	});
}

export async function _buyMembership(item) {
	return await axios.post(`${process.env.REACT_APP_API_URL}/invoice/subscription`,{
		"id": item.id,
		"membershipName": item.name,
		"price": item.amountStart,
		"currency": item.currency,
		"frequency": item.frequency,
		"planId": item.id
	}, {
		headers: { 'X-Auth-Token': getToken() },
	
	});
}

export async function _getPlans() {
	return await axios.get(`${process.env.REACT_APP_API_URL}/invoice/allPlans`, {
		headers: { 'X-Auth-Token': getToken() },
	});
}

