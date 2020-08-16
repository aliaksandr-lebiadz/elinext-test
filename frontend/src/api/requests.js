import fetch from 'isomorphic-fetch';
import ROUTES from './routes';

const post = (route, data) => {
    return fetch(route, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if(response.status === 200 || response.status === 201) {
            return response.json();
        } else if(response.status === 204) {
            return response;
        } else {
            throw new Error("Login error");
        }
    });
}

export const login = (login, password) => {
    return post(ROUTES.LOGIN, { username: login, password });
};

export const signUp = (login, password) => {
    return post(ROUTES.SIGN_UP, { username: login, password });
};