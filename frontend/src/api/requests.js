import fetch from 'isomorphic-fetch';
import ROUTES from './routes';

const handleResponse = response => {
    if(response.status === 200 || response.status === 201) {
        return response.json();
    } else if(response.status === 204) {
        return response;
    } else {
        throw new Error("Something went wrong...");
    }
};

const getHeaders = token => {
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/json');
    if(token) {
        headers.append('Authorization', `Bearer ${token}`);
    }
    return headers;
};

const get = (route, token) => fetch(route, { headers: getHeaders(token) })
    .then(handleResponse);

const post = (route, data, token) => fetch(route, {
        method: 'POST',
        headers: getHeaders(token),
        body: JSON.stringify(data)
    })
    .then(handleResponse);

export const login = (login, password) => post(ROUTES.LOGIN, { username: login, password });

export const signUp = (login, password) => post(ROUTES.SIGN_UP, { username: login, password });

export const getRoomTypes = token => get(ROUTES.GET_ROOM_TYPES, token);

export const getRoomsByType = (type, token) =>
    get(`${ROUTES.GET_ROOMS_BY_TYPE}?type=${type}`, token);

export const addReservation = (reservation, token) =>
    post(ROUTES.ADD_RESERVATION, reservation, token);