import fetch from 'isomorphic-fetch';
import ROUTES from './routes';

export const login = (login, password) => {
    return fetch(ROUTES.LOGIN, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: login, password })
    })
    .then(response => {
        if(response.status !== 200) {
            throw new Error("Login error");
        }
        return response.json();
    });
};

export const signUp = (login, password) => {

}