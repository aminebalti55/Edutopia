import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; // <-- Import the FormsModule

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductDetailsComponent } from './componant/product-details/product-details.component';
import { ShopComponent } from './componant/shop/shop.component';
import { ContactComponent } from './componant/contact/contact.component';
import { LoginComponent } from './componant/login/login.component';
import { HomeComponent } from './componant/home/home.component';
import { UserService } from './componant/login/UserService';
import { DishService } from './componant/login/dishService';
import { RatingService } from './componant/login/ratingService';

import { HomeLoggedComponent } from './componant/HomeLogged/HomeLogged.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductDetailsComponent,
    ShopComponent,
    ContactComponent,
    LoginComponent,
    HomeComponent,
    HomeLoggedComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,  HttpClientModule ,FormsModule
  ],
  providers: [UserService ,DishService,RatingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
