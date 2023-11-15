const imageHandler = () => {
  const imageList = document.getElementById("imageList");
  if (!imageList) {
    return;
  }
  const boxImageItems = document.querySelectorAll(".boxImageItem");
  let imageLength = boxImageItems.length;

  // view image item
  const imageItems = document.querySelectorAll(".imageItem");
  imageItems.forEach((item) => {
    const imagePrevView = item
      .closest(".imageGroup")
      .querySelector(".imagePrevView");
    item.onclick = (e) => {
      imageItems.forEach((i) => i.classList.remove("imageActive"));
      e.target.classList.add("imageActive");
      imagePrevView.src = e.target.src;
      // edit variant image
      const variantImageId =
        imagePrevView.parentElement.querySelector(".variantImageId");
      if (!variantImageId) {
        return;
      }
      const imageId = e.target.id.replace("image", "");
      variantImageId.value = imageId;
      variantImageId.name = variantImageId.name.replace("img", "image");
    };
  });
  // remove image item when edit product
  boxImageItems.forEach((box) => {
    const btnRemove = box.querySelector(".btnRemoveImage");
    if (!btnRemove) {
      return;
    }
    btnRemove.onclick = (e) => {
      box.remove();
      imageLength = imageLength - 1;
      imageHandler();
    };
  });

  const removeAllBtn = document.querySelector("#removeAllImageItems");
  if (!removeAllBtn) {
    return;
  }
  removeAllBtn.onclick = (e) => {
    const imageList = document.querySelectorAll(".boxImageItem");
    imageLength = 0;
    imageList.forEach((box) => box.remove());
    imageHandler();
  };
  // add image by src
  const addImageSrc = document.getElementById("addImageSrc");
  if (addImageSrc) {
    addImageSrc.onclick = (e) => {
      const inputImageSrc = document.getElementById("inputImageSrc");
      const newSrc = inputImageSrc.value;

      if (newSrc === "") {
        console.log("new src is empty!");
        return;
      }

      const newBoxImageItem = document.createElement("div");
      newBoxImageItem.classList.add("px-2");
      newBoxImageItem.classList.add("boxImageItem");
      const content = `<div class="imageEdit">
                          <img class="imageItem" role="button" src="${newSrc}" alt="">
                          <img class="btnRemoveImage m-2" role="button" src="/admin/assets/img/icons/myicon/delete.svg" alt="">
                      </div>
                      <input type="hidden" name="images[${imageLength}].src" value="${newSrc}">`;
      newBoxImageItem.innerHTML = content;
      imageList.appendChild(newBoxImageItem);
      imageLength = imageLength + 1;
      inputImageSrc.value = "";

      imageHandler();
    };
  }

  // upload images
  const imageFiles = document.getElementById("imageFiles");
  if (!imageFiles) {
    return;
  }
  imageFiles.onchange = (e) => {
    const fileUploadItemBox = imageList.querySelectorAll(".fileUploadItemBox");
    fileUploadItemBox.forEach((i) => i.remove());
    imageLength = imageLength - fileUploadItemBox.length;
    const files = e.target.files;
    Array.from(files).map((file) => {
      var reader = new FileReader();
      reader.onload = function (ev) {
        const newSrc = ev.target.result;
        const content = `<div class=" px-2 boxImageItem fileUploadItemBox">
                          <div class="imageEdit">
                            <img class="imageItem" role="button" src="${newSrc}" alt="">
                            <img class="btnRemoveImage m-2" role="button" src="/admin/assets/img/icons/myicon/delete.svg" alt="">
                          </div>
                          <input class="src" type="hidden" name="images[${imageLength}].src" value="${newSrc}">
                        </div>`;
        imageList.innerHTML += content;
        imageHandler();
      };
      reader.readAsDataURL(file);
    });
  };

  const reloadImageItemIndex = () => {
    const currentBoxImageItems = document.querySelectorAll(".boxImageItem");
    currentBoxImageItems.forEach((currentBox, index) => {
      const inputSrcImage = currentBox.querySelector(".src");
      const inputIdImage = currentBox.querySelector(".id");
      inputSrcImage.name = `images[${index}].src`;
      if (inputIdImage) {
        inputIdImage.name = `images[${index}].id`;
      }
    });
  };
  reloadImageItemIndex();
};
const collectionHandler = () => {
  const collectionsList = document.getElementById("collectionsList");
  if (!collectionsList) {
    return;
  }
  const collectionItemsBox =
    collectionsList.querySelectorAll(".collectionItemBox");
  let collectionLength = collectionItemsBox.length;
  collectionItemsBox.forEach((item) => {
    const btnRemove = item.querySelector(".removeCollectionItem");
    if (!btnRemove) {
      return;
    }
    btnRemove.onclick = (e) => {
      item.remove();
      collectionLength = collectionLength - 1;
      const currentCollectionItemsBox =
        collectionsList.querySelectorAll(".collectionItemBox");
      currentCollectionItemsBox.forEach((currentBox, index) => {
        const inputCollectionId =
          currentBox.querySelector(".inputCollectionId");
        const inputCollectionTitle = currentBox.querySelector(
          ".inputCollectionTitle"
        );
        inputCollectionTitle.name = `collections[${index}].title`;
        if (inputCollectionId) {
          inputCollectionId.name = `collections[${index}].id`;
        }
      });
    };
  });

  const addToCollectionBtn = document.getElementById("addToCollectionBtn");
  if (!addToCollectionBtn) {
    return;
  }
  const inputNewCollection = document.getElementById("inputNewCollection");
  const addCollection = () => {
    const newCollectionValue = inputNewCollection.value;
    if (newCollectionValue === "") {
      console.log("new collection value is empty!");
      return;
    }
    const valueExists = Array.from(
      collectionsList.querySelectorAll(".collectionItemBox")
    ).some(
      (item) =>
        item.querySelector(".inputCollectionTitle").value === newCollectionValue
    );
    if (valueExists) {
      console.log("collection value already exists");
      inputNewCollection.value = "";
      return;
    }

    const content = `<div style="display:inline-block; border:2px solid #ccc;" class="rounded px-1 m-1 collectionItemBox">
                        <span>${newCollectionValue}</span>
                        <input class="inputCollectionTitle" type="hidden" name="collections[${collectionLength}].title" value="${newCollectionValue}">
                        <span class="px-1 removeCollectionItem" role="button">×</span>
                    </div>`;
    collectionsList.innerHTML += content;
    collectionLength = collectionLength + 1;
    inputNewCollection.value = "";
    inputNewCollection.focus();
    collectionHandler();
  };
  addToCollectionBtn.onclick = (e) => {
    addCollection();
  };
  inputNewCollection.onkeyup = (e) => {
    if (e.code === "Enter") {
      addCollection();
    }
  };
};
const productTagsHandler = () => {
  const productTagsList = document.getElementById("productTagsList");
  if (!productTagsList) {
    return;
  }
  const productTagItemBox =
    productTagsList.querySelectorAll(".productTagItemBox");
  let productTagsLength = productTagItemBox.length;
  productTagItemBox.forEach((item) => {
    const btnRemove = item.querySelector(".removeProductTagItem");
    if (!btnRemove) {
      return;
    }
    btnRemove.onclick = (e) => {
      item.remove();
      productTagsLength = productTagsLength - 1;
      const currentProductTagItemBox =
        productTagsList.querySelectorAll(".productTagItemBox");
      currentProductTagItemBox.forEach((currentBox, index) => {
        const inputProductTagId =
          currentBox.querySelector(".inputProductTagId");
        const inputProductTagValue = currentBox.querySelector(
          ".inputProductTagValue"
        );
        inputProductTagValue.name = `collections[${index}].title`;
        if (inputProductTagId) {
          inputProductTagId.name = `collections[${index}].id`;
        }
      });
    };
  });

  const addTagBtn = document.getElementById("addTagBtn");
  if (!addTagBtn) {
    return;
  }
  const inputNewProductTag = document.getElementById("inputNewProductTag");
  const addProductTag = () => {
    const newProductTagValue = inputNewProductTag.value;
    if (newProductTagValue === "") {
      console.log("new product tag value is empty!");
      return;
    }
    const valueExists = Array.from(
      productTagsList.querySelectorAll(".productTagItemBox")
    ).some(
      (item) =>
        item.querySelector(".inputProductTagValue").value === newProductTagValue
    );
    if (valueExists) {
      console.log("product tag value already exists");
      inputNewProductTag.value = "";
      return;
    }

    const content = `<div style="display:inline-block; border:2px solid #ccc;" class="rounded px-1 m-1 productTagItemBox">
                        <span>${newProductTagValue}</span>
                        <input class="inputProductTagValue" type="hidden" name="productTags[${productTagsLength}].value" value="${newProductTagValue}">
                        <span class="px-1 removeProductTagItem" role="button">×</span>
                    </div>`;
    productTagsList.innerHTML += content;
    productTagsLength = productTagsLength + 1;
    inputNewProductTag.value = "";
    inputNewProductTag.focus();
    productTagsHandler();
  };
  addTagBtn.onclick = (e) => {
    addProductTag();
  };
  inputNewProductTag.onkeyup = (e) => {
    if (e.code === "Enter") {
      addProductTag();
    }
  };
};
const optionsHandler = () => {
  const optionsList = document.getElementById("optionsList");
  if (!optionsList) {
    return;
  }
  const optionItemsBox = optionsList.querySelectorAll(".optionItemBox");
  let optionsLength = optionItemsBox.length;
  optionItemsBox.forEach((box) => {
    const btnRemoveOption = box.querySelector(".btnRemoveOption");
    const addOptionValueBtn = box.querySelector(".addOptionValueBtn");
    const optionValueItemsBox = box.querySelectorAll(".optionValueItemBox");
    const optionValuesList = box.querySelector(".optionValuesList");
    optionValueItemsBox.forEach((boz) => {
      const removeBtn = boz.querySelector(".removeOptionValueBtn");
      removeBtn.onclick = () => {
        boz.remove();
        optionsHandler();
      };
    });

    btnRemoveOption.onclick = (e) => {
      box.remove();
      optionsHandler();
    };
    
    const addOptionValue = (inputOptionValue) => {
      const optionValue = inputOptionValue.value;
      if (!optionValue) {
        return;
      }
      const content = `<div style="display:inline-block; border:2px solid #ccc;" class="rounded px-1 m-1 optionValueItemBox">
                          <span>${optionValue}</span>
                          <input class="inputOptionValueValue" type="hidden" name="options[0].optionValues[0].value"  value="${optionValue}">
                              <span class="removeOptionValueBtn px-1" role="button">×</span>
                      </div>`;
      optionValuesList.innerHTML += content;
      inputOptionValue.value = "";
      inputOptionValue.focus()
      optionsHandler();
    };
    addOptionValueBtn.onclick = (e) => {
      const inputOptionValue =
      e.target.parentElement.querySelector(".inputOptionValue");
      addOptionValue(inputOptionValue);
    };
    const inputOptionValue = box.querySelector(".inputOptionValue")
    inputOptionValue.onkeyup =(e)=>{
      if(e.code ==="Enter"){
        addOptionValue(inputOptionValue);
      }
    }
  });

  const addOptionBtn = document.getElementById("addOptionBtn");

  addOptionBtn.onclick = (e) => {
    const content = `<div class="card accordion-item optionItemBox active">
                        <h2 class="accordion-header" id="">
                            <button type="button" class="accordion-button collapsed" data-bs-toggle="collapse" aria-expanded="false" data-bs-target="#option${optionsLength}" aria-controls="option${optionsLength}">Option item ${
      optionsLength + 1
    }</button>
                        </h2>
                        <div id="option${optionsLength}" class="accordion-collapse px-3 collapse" data-bs-parent="#options" style="">
                            <div class="accordion-body">
                                <div class="mb-3 row optionName">
                                    <label for="optionName${optionsLength}" class="col-md-2 small">Name</label>
                                    <div class="col-md-10">
                                        <input class="inputOptionNameItem form-control-sm  form-control" type="text" id="optionName${optionsLength}" name="options[${optionsLength}].name">
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label  class="col-md-2 small">Values</label>
                                    <div class="optionValuesList px-1 col-md-10">
                                        
                                    </div>
                                    <div class="row">
                                        <div class="col-md-2"></div>
                                        <div class="col-md-10 row">
                                            <div class="col-10">
                                                <input class="inputOptionValue form-control-sm  form-control " type="text" list="">
                                            </div>
                                            <button type="button" class="addOptionValueBtn btn col-2 btn-sm btn-outline-primary">Add value
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="text-align: right;" class="mx-3 mb-3">
                            <img class="btnRemoveOption m-2" role="button" src="/admin/assets/img/icons/myicon/delete.svg" alt="">
                        </div>
                    </div>`;
    optionsList.innerHTML += content;
    const lastBtnOptionItemBox = optionsList.querySelector(
      ".optionItemBox:last-child button.accordion-button"
    );
    lastBtnOptionItemBox.click();
    optionsHandler();
  };
  const reloadOptionIndex = () => {
    const currentOptionItemsBox =
      optionsList.querySelectorAll(".optionItemBox");
    currentOptionItemsBox.forEach((currentBox, index) => {
      const inputOptionNameItem = currentBox.querySelector(
        ".inputOptionNameItem"
      );
      const inputOptionIdItem = currentBox.querySelector(".inputOptionIdItem");

      inputOptionNameItem.name = `options[${index}].name`;
      if (inputOptionIdItem) {
        inputOptionIdItem.name = `options[${index}].id`;
      }

      const currentOptionValueItemsBox = currentBox.querySelectorAll(
        ".optionValueItemBox"
      );
      currentOptionValueItemsBox.forEach((currentBoz, indez) => {
        const inputOptionValueValue = currentBoz.querySelector(
          ".inputOptionValueValue"
        );
        inputOptionValueValue.name = `options[${index}].optionValues[${indez}].value`;
        const inputOptionValueId = currentBoz.querySelector(
          ".inputOptionValueId"
        );
        if (inputOptionValueId) {
          inputOptionValueId.name = `options[${index}].optionValues[${indez}].id`;
        }
      });
    });
  };
  reloadOptionIndex();
};
const bodyHtmlHandler = () => {
  const bodyHtmlInput = document.querySelector(".productBodyHtml textarea");
  if (!bodyHtmlInput) {
    return;
  }
  const prevBodyHtml = document.querySelector(".productPrevBodyHtml div");
  bodyHtmlInput.oninput = (e) => {
    const content = e.target.value;
    prevBodyHtml.innerHTML = content;
  };
};
const reloadImage = () => {
  const images = document.querySelectorAll(".imageList img");
  const startTime = performance.now();

  images.forEach((image) => {
    image.src = image.src;
    image.onerror = (e) => {
      console.log("reloadImage");
      if (performance.now() - startTime < 10000) image.src = e.target.src;
    };
  });
};
/**
 * customer begin
 */
const uploadAvatar = () => {
  const uploadInput = document.getElementById("uploadInput");
  const avatarImage = document.getElementById("avatarImage");

  if (!uploadInput) {
    return;
  }
  uploadInput.onchange = (e) => {
    const files = e.target.files;
    const reader = new FileReader();
    reader.onload = function (ev) {
      const newSrc = ev.target.result;
      avatarImage.src = newSrc;
    };
    reader.readAsDataURL(files[0]);
  };
};
const togglePwd = () => {
  const togglePwd = document.querySelectorAll(".togglePassword ");
  if (!togglePwd) {
    return;
  }
  togglePwd.forEach((item) => {
    item.onclick = (e) => {
      const input = e.target.closest("div").querySelector("input");
      input.type = input.type == "text" ? "password" : "text";
      e.target
        .closest("div")
        .querySelector("i")
        .classList.remove(input.type == "text" ? "bx-hide" : "bx-show");
      e.target
        .closest("div")
        .querySelector("i")
        .classList.add(input.type == "text" ? "bx-show" : "bx-hide");
    };
  });
};
// order
const searchCustomerHandler = () => {
  console.log("ioio");
  const customerInputSearch = document.getElementById("customerInputSearch");
  console.log(customerInputSearch);
  if (!customerInputSearch) {
    return;
  }
  const listSearchCustomer = document.querySelector(".listSearchCustomer");

  customerInputSearch.onkeyup = async (e) => {
    const name = e.target.value;
    listSearchCustomer.style.display = "block";
    const customers = await searchCustomer(name);
    const ulElement = listSearchCustomer.querySelector("ul");
    ulElement.innerHTML = "";
    if (customers.length === 0) {
      ulElement.innerHTML += `<h6 class="text-center">Not found</h6> `;
    }
    customers.forEach((customer) => {
      const content = `<li id="${customer.id}" role="button">
                        <div class="cart mb-3">
                            <div class="row">
                                <div class="col-sm-2">
                                    <img src="${customer.image}"
                                        alt="img">
                                </div>
                                <div class="col-sm-10 username">${customer.username}</div>
                            </div>
                        </div>
                      </li>`;
      ulElement.innerHTML += content;
    });
    selectCustomerHandler();
  };
  customerInputSearch.onblur = () => {
    setTimeout(() => {
      listSearchCustomer.style.display = "none";
    }, 1000);
  };
  const searchCustomer = async (value) => {
    const res = await fetch(`/admin/api/customers.json?name=${value}`);
    const data = await res.json();
    return data;
  };
};
const selectCustomerHandler = () => {
  const customerSelected = document.getElementById("customerSelected");
  const listSearchCustomer = document.querySelector(".listSearchCustomer");
  const customersBox = listSearchCustomer.querySelectorAll("li");
  customersBox.forEach((li) => {
    li.onclick = (e) => {
      console.log(e);
      listSearchCustomer.style.display = "none";
      const liElement = e.target.closest("li");
      const id = liElement.id;
      const image = liElement.querySelector("img").src;
      const username = liElement.querySelector(".username").innerText;
      const content = `<div  class="row">
                        <input type="hidden" name="customer.id" value="${id}">
                        <div class="col-sm-2">
                            <img style="width: 100%;"
                                src="${image}" alt="img">
                        </div>
                        <div style="display: flex; align-items: center;" class="col-sm-10 username">${username}</div>
                      </div>`;
      customerSelected.innerHTML = content;
    };
  });
};
const load = () => {
  // product
  imageHandler();
  collectionHandler();
  productTagsHandler();
  optionsHandler();
  bodyHtmlHandler();
  reloadImage();
  // customer
  uploadAvatar();
  togglePwd();
  // order
  searchCustomerHandler();

  const forms = document.querySelectorAll("form.enterDisable");
  forms.forEach((item) => {
    item.onkeydown = (e) => {
      if (e.code === "Enter") {
        e.preventDefault();
      }
    };
  });
};

load();
