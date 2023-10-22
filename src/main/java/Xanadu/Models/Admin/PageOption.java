package Xanadu.Models.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageOption {
    private List<Integer> sizeOptions;
    private Integer pageSize;
    private Integer pageNumber;
    private String url;
}
