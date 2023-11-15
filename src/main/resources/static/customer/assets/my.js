const viewProductHandler = () => {
  const productItems = document.querySelectorAll(".productItem");
  productItems.forEach((item) => {
    const imgMain = item.querySelector("img");
    const srcMain = imgMain.src;
    const options = item.querySelectorAll(".optionItem");
    options.forEach((option) => {
      option.onmouseover = (e) => {
        imgMain.src = option.src;
      };
      option.onmouseout = (e) => {
        imgMain.src = srcMain;
      };
    });
  });

  const mainImage = document.querySelector(".mainImage img");
  const imageItems = document.querySelectorAll(".listImage .imageItem");
  imageItems.forEach((item) => {
    item.onclick = () => {
      mainImage.src = item.src;
    };
  });
};

let currentVariant;
const selectVariantHandler = () => {
  const mainImage = document.querySelector(".mainImage img");
  const options = document.querySelectorAll(".optionValue");
  const title = Array.from(options).reduce((prev, option) => {
    if (option.checked) {
      if (prev.length === 0) {
        return option.value;
      } else {
        return prev + " / " + option.value;
      }
    }
    return prev;
  }, "");

  if (typeof product !== "undefined" && product !== null) {
    product.variants.forEach((variant) => {
      if (variant.title === title) {
        currentVariant = variant;
        mainImage.src = variant.image.src;
      }
    });
  }
  options.forEach((option) => {
    option.onclick = () => {
      selectVariantHandler();
    };
  });
};
const selectQuantityHandler = () => {
  const quantityGroups = document.querySelectorAll(".addQuantityGroup");
  quantityGroups.forEach((quantityGroup) => {
    const quantityMinus = quantityGroup.querySelector(".quantityMinus");
    const quantityPlus = quantityGroup.querySelector(".quantityPlus");
    const input = quantityGroup.querySelector("input");

    quantityMinus.onclick = () => {
      const quantity = input.value;
      if (quantity * 1 > 1) {
        input.value = quantity * 1 - 1;
      }
    };
    quantityPlus.onclick = () => {
      const quantity = input.value;
      if (quantity * 1 < input.max * 1) {
        input.value = quantity * 1 + 1;
      }
    };
  });
};

const addToCartHandler = () => {
  const addToCartBtn = document.getElementById("addToCart");
  if (addToCartBtn) {
    addToCartBtn.onclick = async () => {
      const quantity = document.querySelector(".addQuantityGroup input").value;
      const url = "/api/v1/cartItems.json";
      const cartItem = {
        quantity,
        variant: currentVariant,
      };
      const res = await fetch(url, {
        method: "POST",
        body: JSON.stringify(cartItem),
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "same-origin",
      });
      if (res.status !== 200) {
        if (res.status === 403) {
          window.location = "/signin.html";
        } else {
          alert("System error!!");
        }
      }
      const data = await res.json();
      const cartSimpleInfo = document.querySelector(".cartSimpleInfo");
      cartSimpleInfo.textContent = data.cartItems.length;
    };
  }
};

const cartHandler = () => {
  const cart = document.getElementById("cart");
  if (cart) {
    const totalPrice = cart.querySelector(".totalPrice");
    const cartItems = cart.querySelectorAll(".cartItem");
    cartItems.forEach((cartItemELement) => {
      const quantityBtns = cartItemELement.querySelectorAll(".quantityBtn");
      quantityBtns.forEach((btn) => {
        btn.addEventListener("click", async (e) => {
          const quantity = cartItemELement.querySelector("input").value;
          const id = cartItemELement.id;
          const cartItem = {
            id,
            quantity,
          };
          const data = await editCartItem(cartItem);
          const subTotalPrice = cartItemELement.querySelector(".subTotalPrice");
          const prevSubTotalPrice =
            subTotalPrice.textContent.replace("$", "") * 1;
          subTotalPrice.textContent = data.subTotalPrice.toFixed(1) + "$";
          totalPrice.textContent =
            (
              totalPrice.textContent.replace("$", "") * 1 -
              prevSubTotalPrice +
              data.subTotalPrice
            ).toFixed(1) + "$";
        });
      });

      const btnRemove = cartItemELement.querySelector(".btnRemoveCartItem");
      btnRemove.onclick = async () => {
        const id = cartItemELement.id;
        const subTotalPrice = cartItemELement.querySelector(".subTotalPrice");
        const prevSubTotalPrice =
          subTotalPrice.textContent.replace("$", "") * 1;
        totalPrice.textContent =
          (
            totalPrice.textContent.replace("$", "") * 1 -
            prevSubTotalPrice
          ).toFixed(1) + "$";
        const data = await removeCartItem(id);
        cartItemELement.remove();
      };
    });
  }
};
const removeCartItem = async (id) => {
  const res = await fetch(`/api/v1/cartItems/${id}.json`, {
    method: "DELETE",
    credentials: "same-origin",
  });
  if (res.status !== 200) {
    if (res.status === 403) {
      window.location = "/signin.html";
    } else {
      alert("System error!!");
    }
  }
  const data = await res.json();
  return data;
};
const editCartItem = async (cartItem) => {
  const res = await fetch(`/api/v1/cartItems/${cartItem.id}.json`, {
    method: "PUT",
    body: JSON.stringify(cartItem),
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "same-origin",
  });
  if (res.status !== 200) {
    if (res.status === 403) {
      window.location = "/signin.html";
    } else {
      alert("System error!!");
    }
  }
  const data = await res.json();
  return data;
};
const checkoutHandler = ()=>{

}
const load = () => {
  viewProductHandler();
  selectQuantityHandler();
  selectVariantHandler();
  addToCartHandler();
  cartHandler();
  checkoutHandler();
};
load();
