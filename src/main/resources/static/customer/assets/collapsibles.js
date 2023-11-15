/** Shopify CDN: Minification failed

Line 16:0 Transforming const to the configured target environment ("es5") is not supported yet
Line 26:0 Transforming const to the configured target environment ("es5") is not supported yet
Line 26:22 Transforming array spread to the configured target environment ("es5") is not supported yet
Line 29:2 Transforming let to the configured target environment ("es5") is not supported yet
Line 31:2 Transforming let to the configured target environment ("es5") is not supported yet
Line 32:2 Transforming let to the configured target environment ("es5") is not supported yet
Line 33:2 Transforming const to the configured target environment ("es5") is not supported yet
Line 34:2 Transforming let to the configured target environment ("es5") is not supported yet
Line 38:19 Transforming array spread to the configured target environment ("es5") is not supported yet
Line 42:2 Transforming let to the configured target environment ("es5") is not supported yet
... and 7 more hidden warnings

**/
const bodyScrollLock = () => {
  return {
    disableBodyScroll: () => {
      document.querySelector("body").style.overflow = "hidden";
    },
    enableBodyScroll: () => {
      document.querySelector("body").style.overflow = "auto";
    },
  }
}
const collapsibles = [...document.getElementsByClassName('collapsible')];

collapsibles.forEach((collapsible) => {
  let thisCollapsible = collapsible;
  
  let promoBar;
  let trashOverlay;
  const contentForLayout = document.getElementById('content-for-layout');
  let menuDrawers = [];
  if (collapsible.classList.contains("trash-ticker__button")){
    thisCollapsible = document.querySelector(".trash-removed__container");
    promoBar = true;
    menuDrawers = [...document.getElementsByClassName("menu-drawer")];
    trashOverlay = document.getElementById("trash-counter-overlay");
  }

  let plusIcon;
  let minusIcon;
  if (collapsible.classList.contains("collapsible-list__item-wrapper")) {
    plusIcon = collapsible.querySelector(".plus")
    minusIcon = collapsible.querySelector(".minus")
  }

  thisCollapsible.dataset.collapsedHeight = thisCollapsible.clientHeight;
  thisCollapsible.dataset.openedHeight = thisCollapsible.scrollHeight;
  thisCollapsible.dataset.collapsed = 'true';

  menuDrawers.forEach(drawer => {
    drawer.style.setProperty('top', `${thisCollapsible.dataset.collapsedHeight}px`)
  })


  const carret = collapsible.getElementsByClassName('chevron')[0];

  let collapsibleParent = null;

  if (collapsible.parentElement.classList.contains('collapsible')) {
    collapsibleParent = collapsible.parentElement;
  }

  if (collapsible.parentElement.parentElement.classList.contains('collapsible')) {
    collapsibleParent = collapsible.parentElement.parentElement;
  }

  function close(){
    thisCollapsible.style.height = `${thisCollapsible.dataset.collapsedHeight}px`;
    thisCollapsible.dataset.collapsed = 'true';
    if (carret) {
      carret.style.transform = 'rotate(0)';
    }

    if (plusIcon) {
      minusIcon.classList.remove("visible")
      plusIcon.classList.add("visible")
    }

    if (promoBar) {
      thisCollapsible.classList.remove('expanded')
      menuDrawers.forEach(drawer => {
        drawer.classList.remove('trash-expanded')
        drawer.style.setProperty('top', `${thisCollapsible.dataset.collapsedHeight}px`)
      })
      try {
        contentForLayout.style.paddingTop = `${parseInt(window.getComputedStyle(contentForLayout)["padding-top"]) - parseInt(thisCollapsible.dataset.openedHeight)}px`
        bodyScrollLock.enableBodyScroll(contentForLayout)
        trashOverlay.style.transform = `translateY(${parseInt(thisCollapsible.dataset.collapsedHeight)}px)`
        trashOverlay.style.zIndex = -10
        trashOverlay.style.height = 0
      } catch (error) {
        console.log(error)
      }
     
    }

    if (collapsibleParent) {
      const newcollapsibleParentOpenHeight = parseInt(collapsibleParent.dataset.openedHeight, 10) - (thisCollapsible.dataset.openedHeight - thisCollapsible.dataset.collapsedHeight);
      collapsibleParent.style.height = `${newcollapsibleParentOpenHeight}px`;
      collapsibleParent.dataset.openedHeight = newcollapsibleParentOpenHeight;
    }
  }
  document.addEventListener('scroll', function (event) {
    /*if(window.pageYOffset > 0) {
      close()
      const promoBar = document.querySelector(".promo-bar");
      const height = promoBar.clientHeight;
      
      promoBar.style.setProperty('transform', `translateY(-${height}px`)
      const globalHeader = document.querySelector(".global-header");
      globalHeader.style.setProperty('transform', `translateY(-${height}px`)
    } else {*/
      const promoBar = document.querySelector(".promo-bar");
      promoBar.style.setProperty('transform', `translateY(0px)`)
     
   /* }*/
  }, true /*Capture event*/);
    if (promoBar) {
      document.addEventListener('click', (e) => {
        e.stopPropagation()

        if (thisCollapsible.dataset.collapsed === 'false' && !thisCollapsible.contains(e.target)) {
          close()
        }
      })
    }
    const contentWrapper = collapsible.querySelector('.collapsible-list__item-content')
  !collapsible.classList.contains("no-children") && collapsible.addEventListener('click', (e) => {
    e.stopPropagation();
    if (e.currentTarget === collapsible || (plusIcon && !contentWrapper.contains(e.currentTarget))) {
      if (thisCollapsible.dataset.openedHeight === '0') {
        thisCollapsible.dataset.collapsedHeight = thisCollapsible.clientHeight;
        thisCollapsible.dataset.openedHeight = thisCollapsible.scrollHeight;
      } else if (thisCollapsible.dataset.collapsed === 'true' && thisCollapsible.dataset.openedHeight != thisCollapsible.scrollHeight) {
        thisCollapsible.dataset.collapsedHeight = thisCollapsible.clientHeight;
        thisCollapsible.dataset.openedHeight = thisCollapsible.scrollHeight;
      }
      if (thisCollapsible.dataset.collapsed === 'true') {
        thisCollapsible.style.height = `${thisCollapsible.dataset.openedHeight}px`;
        thisCollapsible.dataset.collapsed = 'false';
        if (carret) {
          carret.style.transform = 'rotate(-180deg)';
        }
        
        if (plusIcon){
          plusIcon.classList.remove("visible")
          minusIcon.classList.add("visible")
        } 

        if (promoBar){
          thisCollapsible.classList.add('expanded')
          menuDrawers.forEach(drawer => {
            drawer.classList.add('trash-expanded')
            drawer.style.setProperty('top', `${thisCollapsible.dataset.openedHeight}px`)
          })
          contentForLayout.style.paddingTop = `${parseInt(window.getComputedStyle(contentForLayout)["padding-top"]) + parseInt(thisCollapsible.dataset.openedHeight)}px`
          bodyScrollLock.disableBodyScroll(contentForLayout)
          trashOverlay.style.transform = `translateY(${parseInt(thisCollapsible.dataset.openedHeight)}px)`
          trashOverlay.style.zIndex = 10
          trashOverlay.style.height = '100vh'
        }

        if (collapsibleParent) {
          const newcollapsibleParentOpenHeight = parseInt(collapsibleParent.dataset.openedHeight, 10) + (thisCollapsible.dataset.openedHeight - thisCollapsible.dataset.collapsedHeight);
          collapsibleParent.style.height = `${newcollapsibleParentOpenHeight}px`;
          collapsibleParent.dataset.openedHeight = newcollapsibleParentOpenHeight;
        }
      } else {
        close()
      }
    }
  });
});


