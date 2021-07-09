import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatListModule} from '@angular/material/list';
import {MAT_SNACK_BAR_DEFAULT_OPTIONS, MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSelectModule} from '@angular/material/select';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatMenuModule} from '@angular/material/menu';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatSortModule} from '@angular/material/sort';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatRadioModule} from '@angular/material/radio';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCheckboxModule} from '@angular/material/checkbox';

const MatererialComponents = [

];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatListModule,
    MatSnackBarModule,
    MatSelectModule,
    MatTooltipModule,
    MatToolbarModule,
    MatTabsModule,
    MatTableModule,
    MatPaginatorModule,
    MatMenuModule,
    MatExpansionModule,
    MatSortModule,
    MatAutocompleteModule,
    MatSlideToggleModule,
    MatRadioModule,
    MatDialogModule,
    MatCheckboxModule
  ],
  exports: [
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatListModule,
    MatSnackBarModule,
    MatSelectModule,
    MatTooltipModule,
    MatToolbarModule,
    MatTabsModule,
    MatTableModule,
    MatPaginatorModule,
    MatMenuModule,
    MatExpansionModule,
    MatSortModule,
    MatAutocompleteModule,
    MatSlideToggleModule,
    MatRadioModule,
    MatDialogModule,
    MatCheckboxModule
  ],
  providers: [{ provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: { duration: 2800, panelClass: ['snackBar'] } }]
})
export class MaterialModule { }
