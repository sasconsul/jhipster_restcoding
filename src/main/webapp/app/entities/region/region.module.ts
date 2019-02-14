import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterRestcodingSharedModule } from 'app/shared';
import {
  RegionComponent,
  RegionDetailComponent,
  RegionUpdateComponent,
  RegionDeletePopupComponent,
  RegionDeleteDialogComponent,
  regionRoute,
  regionPopupRoute
} from './';

const ENTITY_STATES = [...regionRoute, ...regionPopupRoute];

@NgModule({
  imports: [JhipsterRestcodingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RegionComponent, RegionDetailComponent, RegionUpdateComponent, RegionDeleteDialogComponent, RegionDeletePopupComponent],
  entryComponents: [RegionComponent, RegionUpdateComponent, RegionDeleteDialogComponent, RegionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterRestcodingRegionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
