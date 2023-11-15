/** Shopify CDN: Minification failed

Line 17:4 Transforming const to the configured target environment ("es5") is not supported yet
Line 18:8 Transforming const to the configured target environment ("es5") is not supported yet
Line 19:8 Transforming const to the configured target environment ("es5") is not supported yet
Line 22:4 Transforming const to the configured target environment ("es5") is not supported yet
Line 23:8 Transforming const to the configured target environment ("es5") is not supported yet
Line 25:122 Transforming array spread to the configured target environment ("es5") is not supported yet
Line 31:6 Transforming const to the configured target environment ("es5") is not supported yet
Line 35:4 Transforming const to the configured target environment ("es5") is not supported yet
Line 36:8 Transforming const to the configured target environment ("es5") is not supported yet
Line 37:8 Transforming let to the configured target environment ("es5") is not supported yet
... and 19 more hidden warnings

**/
document.addEventListener('DOMContentLoaded', () => {  
    const getFavoritesLocalValue = () => {
        const favorites_data = localStorage.getItem("ubb-favorites");
        const favorites = !!favorites_data ? JSON.parse(favorites_data) : [];
        return favorites
    }
    const saveFavoriteLocally = (value, state) => {
        const favorites = getFavoritesLocalValue();
        if(favorites.length) {
          localStorage.setItem("ubb-favorites", JSON.stringify(state ? favorites.filter((item) => item.id != value.id) : [...favorites, value]))
        } else {
          localStorage.setItem("ubb-favorites", JSON.stringify(state ? [] : [value]))
        }
      }

      const unique = (array, propertyName) => {
        return array.filter((e, i) => array.findIndex(a => a[propertyName] === e[propertyName]) === i);
    }
    
    const createMetafield = (value, local, isRemove) => {
        const stored = window.UBBFavorites;
        let localBuild = [];
        let storeBuild = [];
        if(local?.length) {
            localBuild = local;
        }
        if(stored?.length) {
            storeBuild = stored;
        }
        const builtArr = [...localBuild, ...storeBuild, value];
        const filteredArr = isRemove ? builtArr.filter((item) => item.id != value.id) : builtArr;
  
        const uniqueValues = unique(filteredArr, "id");
        localStorage.setItem("ubb-favorites", JSON.stringify(uniqueValues));
        window.UBBFavorites = uniqueValues;
        return fetch(`/a/ubb/metafields/create?customerId=${window.customerId}`, {
                method: 'POST',
                headers: {
                    'X-Shopify-Access-Token': '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',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    'metafield': {
                        'namespace': 'meta',
                        'key': "favorites",
                        'value': `${JSON.stringify([...uniqueValues])}`,
                        'type': "json"
                    }
                })
              });
    };
    const getVariantData = (product_form_id) => {
        const image = $('#image-container-favorites > img').attr('src');
        const id = $(`#${product_form_id} input[name='id']`).val();
        const size = $(".size-option-input.checked").attr('value');
        const color = $(".size-color-input.checked").attr('value');
        const name =  $("#title-container-favorites .title").text();
        const price = $("#price-container-favorites .price").text();
        const obj = { image, id, name, price, size, color }
        console.log("obj: ", obj)
        return obj;
      }

    $(".delete-item").on("click", (evt) => {
        evt.stopPropagation();
        evt.stopImmediatePropagation();
        const image = $(evt.currentTarget).parent().parent().find(".media > img").html();
        const price = $(evt.currentTarget).parent().parent().find(".price__container .price-item").html();
        const title = $(evt.currentTarget).parent().parent().find(".card__heading a").html();
        $("#price-container-favorites .price").text(price);
        $("#title-container-favorites .title").text(title);
        $("#image-container-favorites").append(image);
        const formData = $(evt.currentTarget).parent().find("variants").html();
        $(".favorite-form").html("");
        $(".favorite-form").html(formData);
        $(".overlay-slideout").toggleClass("active");
        $(".favorites-slideout").toggleClass("active");
        $(".close-slideout").toggleClass("active");
        $(".favorite-form .color-swatch").on("click", (evt) => {
            $(".size-color-input").removeClass("checked");
            var input = $("input[id='" + $(evt.currentTarget).attr('for') + "']");
            $(input).toggleClass("checked");
        });
        $(".favorite-form .size-option").on("click", (evt) => {
            $(".size-option-input").removeClass("checked");
            var input = $("input[id='" + $(evt.currentTarget).attr('for') + "']");
            $(input).toggleClass("checked");
        });
        $(".continue-shopping").on("click", (evt) => {
            evt.stopPropagation();
            evt.preventDefault();
            $(".overlay-slideout").toggleClass("active");
            $(".favorites-slideout").toggleClass("active");
            $(".close-slideout").toggleClass("active");
        });

        $(".add-to-wishlist").on("click", (evt) => {
            evt.stopPropagation();
            evt.preventDefault();
            
            if(window.customerId) {
                createMetafield(getVariantData($(evt.currentTarget).attr("formid")), getFavoritesLocalValue(), false).then(() => {
                    $(".overlay-slideout").toggleClass("active");
                    $(".favorites-slideout").toggleClass("active");
                    $(".close-slideout").toggleClass("active");
                });
            
            } else {
                saveFavoriteLocally(getVariantData(), false);
                $(".overlay-slideout").toggleClass("active");
                $(".favorites-slideout").toggleClass("active");
                $(".close-slideout").toggleClass("active");
            }
        });
        $("div.favorites-slideout.active > div.favorite-form > variant-radios > fieldset:nth-child(1) > label[labelid='0']").click();
        $("div.favorites-slideout.active > div.favorite-form > variant-radios > fieldset:nth-child(2) > label[labelid='0']").click();
    });

    $(".overlay-slideout").on("click", (evt) => {
        $(".overlay-slideout").toggleClass("active");
        $(".favorites-slideout").toggleClass("active");
        $(".close-slideout").toggleClass("active");
    });
    $(".close-slideout").on("click", (evt) => {
        $(".overlay-slideout").toggleClass("active");
        $(".favorites-slideout").toggleClass("active");
        $(".close-slideout").toggleClass("active");
    });
    
    
})