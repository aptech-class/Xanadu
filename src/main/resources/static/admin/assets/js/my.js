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
      reloadImageItemIndex();
    };
  });
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

  const removeAllBtn = document.querySelector("#removeAllImageItems");
  if (!removeAllBtn) {
    return;
  }
  removeAllBtn.onclick = (e) => {
    const imageList = document.querySelectorAll(".boxImageItem");
    imageLength = 0;
    imageList.forEach((box) => box.remove());
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
    reloadImageItemIndex();
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
                          <input type="hidden" name="images[${imageLength}].src" value="${newSrc}">
                        </div>`;
        imageList.innerHTML += content;
        imageHandler();
      };
      reader.readAsDataURL(file);
    });
  };
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
  addToCollectionBtn.onclick = (e) => {
    const inputNewCollection = document.getElementById("inputNewCollection");
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

    collectionHandler();
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
  addTagBtn.onclick = (e) => {
    const inputNewProductTag = document.getElementById("inputNewProductTag");
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

    productTagsHandler();
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

    addOptionValueBtn.onclick = (e) => {
      const inputOptionValue =
        e.target.parentElement.querySelector(".inputOptionValue");
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
      optionsHandler();
    };
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
const load = () => {
  imageHandler();
  collectionHandler();
  productTagsHandler();
  optionsHandler();

  const forms = document.querySelectorAll("form");
  forms.forEach((item) => {
    item.onkeydown = (e) => {
      if (e.code === "Enter") {
        e.preventDefault();
      }
    };
  });
};

load();
