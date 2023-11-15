/** Shopify CDN: Minification failed

Line 12:0 Transforming class syntax to the configured target environment ("es5") is not supported yet
Line 13:13 Transforming object literal extensions to the configured target environment ("es5") is not supported yet
Line 15:4 Transforming const to the configured target environment ("es5") is not supported yet
Line 18:6 Transforming const to the configured target environment ("es5") is not supported yet
Line 24:16 Transforming object literal extensions to the configured target environment ("es5") is not supported yet
Line 25:4 Transforming const to the configured target environment ("es5") is not supported yet
Line 26:4 Transforming const to the configured target environment ("es5") is not supported yet

**/
class ShowMoreButton extends HTMLElement {
  constructor() {
    super();
    const button = this.querySelector('button');
    button.addEventListener('click', (event) => {
      this.expandShowMore(event);
      const nextElementToFocus = event.target.closest('.parent-display').querySelector('.show-more-item')
      if (nextElementToFocus && !nextElementToFocus.classList.contains('hidden')) {
        nextElementToFocus.querySelector('input').focus()
      }
    });
  }
  expandShowMore(event) {
    const parentDisplay = event.target.closest('[id^="Show-More-"]').closest('.parent-display');
    const parentWrap = parentDisplay.querySelector('.parent-wrap');
    this.querySelectorAll('.label-text').forEach(element => element.classList.toggle('hidden'));
    parentDisplay.querySelectorAll('.show-more-item').forEach(item => item.classList.toggle('hidden'))
  }
}

customElements.define('show-more-button', ShowMoreButton);

