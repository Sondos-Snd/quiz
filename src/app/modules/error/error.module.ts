import { NgModule } from '@angular/core';
import { ErrorRoutingModule } from './error-routing.module';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { AngularSvgIconModule } from 'angular-svg-icon';
import { AuthInterceptor } from '../auth/auth.interceptor';

@NgModule({ declarations: [], imports: [ErrorRoutingModule, AngularSvgIconModule.forRoot()], 
    providers: [    provideHttpClient(withInterceptorsFromDi()),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },] })
export class ErrorModule {}
