import {ModuleWithProviders, NgModule} from '@angular/core';
import {MathServiceImpl} from './math.service';
import {MathDirective} from './math.directive';

@NgModule({
  declarations: [MathDirective],
  exports: [MathDirective]
})
export class MathModule {
  constructor(mathService: MathServiceImpl) {
    // see https://docs.mathjax.org/en/latest/advanced/dynamic.html
    const script = document.createElement('script') as HTMLScriptElement;
    script.type = 'text/javascript';
    script.src = 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=MML_CHTML';
    script.async = true;

    document.getElementsByTagName('head')[0].appendChild(script);

    const config = document.createElement('script') as HTMLScriptElement;
    config.type = 'text/x-mathjax-config';
    config.text = `
    MathJax.Hub.Config({
        skipStartupTypeset: true,
        MathML: {
          extensions: ["mml3.js"]
        },
        CommonHTML: {
          scale: 180
        }
      });
      MathJax.Hub.Register.StartupHook('End', () => {
        window.hubReady.next();
        window.hubReady.complete();
      });
    `;

    document.getElementsByTagName('head')[0].appendChild(config);
  }


  public static forRoot(): ModuleWithProviders {
    return {
      ngModule: MathModule,
      providers: [{provide: MathServiceImpl, useClass: MathServiceImpl}]
    };
  }
}
