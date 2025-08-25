import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { AngularSvgIconModule } from 'angular-svg-icon';

import { LayoutRoutingModule } from './layout-routing.module';
import { AuthInterceptor } from '../auth/auth.interceptor';
@NgModule({ imports: [LayoutRoutingModule, AngularSvgIconModule.forRoot()], 
    providers: [    provideHttpClient(withInterceptorsFromDi()),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },] })
export class LayoutModule {}
