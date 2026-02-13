import { defineStore } from 'pinia';
import axios from 'axios';

const API_URL = import.meta.env.VITE_API_BASE_URL;

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: JSON.parse(localStorage.getItem('user')),
        token: localStorage.getItem('token'),
    }),
    actions: {
        async login(username, password) {
            try {
                const response = await axios.post(API_URL + 'login', {
                    username,
                    password
                });
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                    localStorage.setItem('token', response.data.token);
                    this.user = response.data;
                    this.token = response.data.token;
                }
                return response.data;
            } catch (error) {
                throw error;
            }
        },
        logout() {
            localStorage.removeItem('user');
            localStorage.removeItem('token');
            this.user = null;
            this.token = null;
        }
    }
});
