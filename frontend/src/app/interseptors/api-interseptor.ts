import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

export class ApiInterseptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: string = localStorage.getItem("token");
    const authReq = req.clone({
      headers: new HttpHeaders(token && token !== "null" ? {
        "Authorization": "Bearer " + token
      } : null )
    });
    return next.handle(authReq);
  }
}
