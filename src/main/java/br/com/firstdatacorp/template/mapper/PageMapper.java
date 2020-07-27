package br.com.firstdatacorp.template.mapper;

import org.springframework.data.domain.Page;

import br.com.firstdatacorp.template.model.DPage;

public final class PageMapper {
    private PageMapper() {

    }

    public static <T> DPage<T> toDPage(Page<T> model) {
        DPage<T> page = new DPage<>();
        page.setCodigoRetorno(0);
        page.setMensagem("Sucesso.");
        page.setConteudo(model.getContent());
        page.setLastPage(model.isLast());
        page.setTotalPages(model.getTotalPages());
        page.setTotalItems(model.getTotalElements());
        page.setCurrentPage(model.getNumber());

        return page;
    }
}
