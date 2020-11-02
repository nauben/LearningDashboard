import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { Token, User } from '../_models';

@Injectable({ providedIn: 'root' })
export class AccountService {
    private tokenSubject: BehaviorSubject<Token>;
    public token: Observable<Token>;
    private userSubject: BehaviorSubject<User>;
    public user: Observable<User>;

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        this.tokenSubject = new BehaviorSubject<Token>(JSON.parse(localStorage.getItem('token')));
        this.token = this.tokenSubject.asObservable();
        this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
        this.user = this.userSubject.asObservable();
    }

    public get tokenValue(): Token {
        return this.tokenSubject.value;
    }
    
    public get userValue(): User {
        return this.userSubject.value;
    }

    login(email:string, password:string) {
        console.log({ email, password });
        var token:any = this.http.post<Token>(`${environment.apiUrl}/login`, { email, password })
            .pipe(map(token => {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('token', JSON.stringify(token));
                this.tokenSubject.next(token);
                console.log(token);
                return token;
                
            }));
        //this.loadBasicUserData();
        return token;
    }

    loadBasicUserData(){
        /*return this.http.get<User>(`${environment.apiUrl}/profile/short`, null)
            .pipe(map(user => {
                localStorage.setItem('user', JSON.stringify(user));
                this.userSubject.next(user);
                console.log(user);
                return user;
            }));*/
    }

    logout() {
        console.log(`${environment.apiUrl}/logout`)
        this.http.post(`${environment.apiUrl}/logout`, null).subscribe({
            next: data => {
                console.log(data);
            },
            error: error => {
                console.error('There was an error!', error);
            }
        })
        //remove user from local storage and set current user to null
        localStorage.removeItem('token');
        this.tokenSubject.next(null);
        this.router.navigate(['/login']);
    }

    register(email: string, password: string) {
        console.log({email, password});
        return this.http.post(`${environment.apiUrl}/register`, {email, password});
    }

    getCurrentUserData() {
        return this.http.get<Token>(`${environment.apiUrl}/profile`);
    }

    /*
    getAll() {
        return this.http.get<User[]>(`${environment.apiUrl}/users`);
    }

    getById(id: string) {
        return this.http.get<User>(`${environment.apiUrl}/users/${id}`);
    }

    update(id, params) {
        return this.http.put(`${environment.apiUrl}/users/${id}`, params)
            .pipe(map(x => {
                // update stored user if the logged in user updated their own record
                if (id == this.userValue.id) {
                    // update local storage
                    const user = { ...this.userValue, ...params };
                    localStorage.setItem('user', JSON.stringify(user));

                    // publish updated user to subscribers
                    this.userSubject.next(user);
                }
                return x;
            }));
    }

    delete(id: string) {
        return this.http.delete(`${environment.apiUrl}/users/${id}`)
            .pipe(map(x => {
                // auto logout if the logged in user deleted their own record
                if (id == this.userValue.id) {
                    this.logout();
                }
                return x;
            }));
    }
    */
}