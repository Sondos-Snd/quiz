import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { environment } from '../../../environments/environment.prod';
import { getKeycloak } from './pages/keycloak-init';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Only attach to backend calls
    if (!req.url.startsWith(environment.apiBaseUrl)) {
      return next.handle(req);
    }

    const kc = getKeycloak();
    const ensureToken$ = from(
      kc.updateToken(60).catch(() => kc.login())
    ).pipe(
      switchMap(() => {
        const token = kc.token;
        const authReq = token
          ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
          : req;
        return next.handle(authReq);
      })
    );

    return ensureToken$;
  }
}
