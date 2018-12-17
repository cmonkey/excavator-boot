package org.excavator.boot.lombok.simulation.processor;

import org.excavator.boot.lombok.simulation.annotation.Getter;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("org.excavator.boot.lombok.simulation.annotation.Getter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GetterProcessor extends AbstractProcessor {
    private Messager messager;
    private JavacTrees javacTrees;
    private Context context;
    private TreeMaker treeMarker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();

        javacTrees = JavacTrees.instance(processingEnv);

        context = ((JavacProcessingEnvironment)processingEnv).getContext();

        treeMarker = TreeMaker.instance(context);

        names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(Getter.class);

        set.forEach(element -> {

            JCTree jcTree = javacTrees.getTree(element);

            jcTree.accept(new TreeTranslator(){
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcVariableDeclList =  List.nil();

                    for (JCTree tree: jcClassDecl.defs){
                        if(Tree.Kind.VARIABLE.equals(tree.getKind())){
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl)tree;
                            jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
                        }
                    }

                    jcVariableDeclList.forEach(jcVariableDecl -> {
                        messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.name + " has heen processed ");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl(jcVariableDecl));
                    });

                    super.visitClassDef(jcClassDecl);
                }
            });
        });
        return false;
    }

    private JCTree makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> listBuffer = new ListBuffer<>();

        listBuffer.append(
                treeMarker.Return(
                        treeMarker.Select(
                                treeMarker.Ident(
                                        names.fromString("this")), jcVariableDecl.getName())));

        JCTree.JCBlock body = treeMarker.Block(0, listBuffer.toList());

        Name name = getNewMethodName(jcVariableDecl.getName());

        return treeMarker.MethodDef(treeMarker.Modifiers(Flags.PUBLIC),
                name,
                jcVariableDecl.vartype,
                List.nil(),
                List.nil(),
                List.nil(),
                body,
                null);


    }

    private Name getNewMethodName(Name name) {

        String s = name.toString();

        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1,name.length()));
    }
}
