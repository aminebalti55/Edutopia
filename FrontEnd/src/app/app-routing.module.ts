import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './componant/login/login.component';
import { HomeComponent } from './componant/home/home.component';
import { ContactComponent } from './componant/contact/contact.component';
import { ProductDetailsComponent } from './componant/product-details/product-details.component';
import { ShopComponent } from './componant/shop/shop.component';
import { HomeLoggedComponent } from './componant/HomeLogged/HomeLogged.component';

import { CommonModule } from '@angular/common';


const routes: Routes = [{ path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent }, { path: 'shop', component: ShopComponent }, { path: 'product-details', component: ProductDetailsComponent }, { path: 'contact', component: ContactComponent },{ path: 'homelogged', component: HomeLoggedComponent }
];


@NgModule({ declarations: [],

  imports:[RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
